package com.fatec.agendify.agendify.service.pendingEvent;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatec.agendify.agendify.dto.event.EventConflictDTO;
import com.fatec.agendify.agendify.dto.event.EventDTO;
import com.fatec.agendify.agendify.dto.pendingEvent.PendingEventUpdateDTO;
import com.fatec.agendify.agendify.mapper.EventMapper;
import com.fatec.agendify.agendify.mapper.PendingEventMapper;
import com.fatec.agendify.agendify.model.event.Event;
import com.fatec.agendify.agendify.model.event.EventLocation;
import com.fatec.agendify.agendify.model.pendingEvent.PendingEvent;
import com.fatec.agendify.agendify.model.user.User;
import com.fatec.agendify.agendify.repository.EventRepository;
import com.fatec.agendify.agendify.repository.PendingEventRepository;
import com.fatec.agendify.agendify.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PendingEventService {
    private final PendingEventRepository pendingEventRepository;
    private final EventRepository eventRepository;
    private final UserService userService;

    public PendingEvent createPendingEvent(PendingEvent pendingEvent) {
        pendingEvent.setEventRequesterId(userService.getAuthenticatedUserId());
        return pendingEventRepository.save(pendingEvent);
    }

    public List<PendingEvent> getAllPendingEvents() {
        return pendingEventRepository.findAll();
    }

    public List<PendingEvent> getRequesterPendingEvents() {
        return pendingEventRepository.findByEventRequesterId(userService.getAuthenticatedUserId());
    }

    public PendingEvent updatePendingEvent(String id, PendingEventUpdateDTO pendingEventUpdateDTO) {
        PendingEvent existingEvent = pendingEventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento pendente não encontrado"));
    
        String authenticatedUserId = userService.getAuthenticatedUserId();
        User.Role role = userService.getAuthenticatedUserRole();
    
        boolean isRequester = existingEvent.getEventRequesterId().equals(authenticatedUserId);
    
        if (!(isRequester || role == User.Role.MASTER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para atualizar este evento");
        }
    
        PendingEventMapper.updateEntityFromDTO(existingEvent, pendingEventUpdateDTO);
        return pendingEventRepository.save(existingEvent);
    }

    public void deletePendingEvent(String id) {
        PendingEvent pending = pendingEventRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    
        if (!pending.getEventRequesterId().equals(userService.getAuthenticatedUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar este evento");
        }
             
        pendingEventRepository.deleteById(id);
    }
    

public Object approvePendingEvent(String id) {

    PendingEvent pendingEvent = pendingEventRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento pendente não encontrado"));

    if (pendingEvent.getResourcesDescription().stream().anyMatch(String::isBlank)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cada recurso deve ser preenchido");
    }

    if (pendingEvent.getRelatedSubjects().stream().anyMatch(String::isBlank)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cada disciplina deve ser preenchida");
    }

    if (pendingEvent.getAuthors().stream().anyMatch(String::isBlank)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cada autor(a) deve ser preenchido");
    }

    List<Event> conflictingEvents = eventRepository.findByDayAndLocation_Name(
        pendingEvent.getDay(), pendingEvent.getLocation().getName()
    );

    LocalTime newStart = pendingEvent.getStartTime();
    LocalTime newEnd = pendingEvent.getEndTime().plus(pendingEvent.getCleanupDuration());

    for (Event existingEvent : conflictingEvents) {
        LocalTime existingStart = existingEvent.getStartTime();
        LocalTime existingEnd = existingEvent.getEndTime().plus(existingEvent.getCleanupDuration());

        boolean isOverlapping = !(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd));

        if (isOverlapping) {
            if (pendingEvent.getPriority().ordinal() > existingEvent.getPriority().ordinal()) {
                existingEvent.setLocation(new EventLocation("A definir", existingEvent.getLocation().getFloor()));
                eventRepository.save(existingEvent);
            } else if (pendingEvent.getPriority().ordinal() < existingEvent.getPriority().ordinal()) {
                pendingEvent.setLocation(new EventLocation("A definir", pendingEvent.getLocation().getFloor()));
            } else {
                return new EventConflictDTO(
                    "Conflito de eventos com mesma prioridade. Escolha uma ação.",
                    EventMapper.toDTO(existingEvent)
                );
            }
        }
    }

    Event createdEvent = EventMapper.toEntityFromPending(pendingEvent);
    createdEvent.setCreatedAt(Instant.now());
    createdEvent.setLastModifiedAt(Instant.now());

    eventRepository.save(createdEvent);
    pendingEventRepository.deleteById(id);

    return EventMapper.toDTO(createdEvent);
}

    public EventDTO resolvePendingEventConflict(String existingEventId, String pendingEventId) {
    Event existingEvent = eventRepository.findById(existingEventId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento existente não encontrado"));

    PendingEvent pendingEvent = pendingEventRepository.findById(pendingEventId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento pendente não encontrado"));

    
    existingEvent.setLocation(new EventLocation("A definir", existingEvent.getLocation().getFloor()));
    eventRepository.save(existingEvent);

    Event newEvent = EventMapper.toEntityFromPending(pendingEvent);
    newEvent.setCreatedAt(Instant.now());
    newEvent.setLastModifiedAt(Instant.now());
    Event savedEvent = eventRepository.save(newEvent);

    pendingEventRepository.deleteById(pendingEventId);

    return EventMapper.toDTO(savedEvent);
}

}