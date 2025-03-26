package com.fatec.agendify.agendify.service.event;

import com.fatec.agendify.agendify.dto.event.EventConflictDTO;
import com.fatec.agendify.agendify.dto.event.EventCreateDTO;
import com.fatec.agendify.agendify.dto.event.EventDTO;
import com.fatec.agendify.agendify.dto.event.EventUpdateDTO;
import com.fatec.agendify.agendify.mapper.EventMapper;
import com.fatec.agendify.agendify.model.Event;
import com.fatec.agendify.agendify.model.EventLocation;
import com.fatec.agendify.agendify.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Object createEvent(EventCreateDTO eventCreateDTO) {
        logger.info("Criando evento: {}", eventCreateDTO);
        
        if (eventCreateDTO.getResourcesDescription().stream().anyMatch(String::isBlank)){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cada recurso deve ser preenchido");
        }

        if (eventCreateDTO.getRelatedSubjects().stream().anyMatch(String::isBlank)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cada disciplina deve ser preenchida");
        }

        if (eventCreateDTO.getAuthors().stream().anyMatch(String::isBlank)){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cada autor(a) deve ser preenchido");
        }        
        
            List<Event> conflictingEvents = eventRepository.findByDayAndLocation_Name(
            eventCreateDTO.getDay(), eventCreateDTO.getLocation().getName()
        );

        
    for (Event existingEvent : conflictingEvents) {
        LocalTime existingStart = existingEvent.getStartTime();
        LocalTime existingEnd = existingEvent.getEndTime().plus(existingEvent.getCleanupDuration());
        LocalTime newStart = eventCreateDTO.getStartTime();
        LocalTime newEnd = eventCreateDTO.getEndTime().plus(eventCreateDTO.getCleanupDuration());

        boolean isOverlapping = !(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd));

        if (isOverlapping) {
            if (eventCreateDTO.getPriority().ordinal() > existingEvent.getPriority().ordinal()) {
                existingEvent.setLocation(new EventLocation("A definir", existingEvent.getLocation().getFloor()));
                eventRepository.save(existingEvent);
            } else if (eventCreateDTO.getPriority().ordinal() < existingEvent.getPriority().ordinal()) {
                eventCreateDTO.setLocation(new EventLocation("A definir", eventCreateDTO.getLocation().getFloor()));
            } else {
              
                return new EventConflictDTO(
                    "Conflito de eventos com mesma prioridade. Escolha uma ação.",
                    EventMapper.toDTO(existingEvent)
                );
            }
        }
    }

        Event event = EventMapper.toEntity(eventCreateDTO);
            event.setCreatedAt(Instant.now());
            event.setLastModifiedAt(Instant.now()); 
        return EventMapper.toDTO(eventRepository.save(event));
    }

    public EventDTO resolveEventConflict(String existingEventId, EventCreateDTO newEventDTO) {
        Event existingEvent = eventRepository.findById(existingEventId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento existente não encontrado"));
    
 
        existingEvent.setLocation(new EventLocation("A definir", existingEvent.getLocation().getFloor()));
        eventRepository.save(existingEvent);
   
        Event newEvent = EventMapper.toEntity(newEventDTO);
            newEvent.setCreatedAt(Instant.now());
            newEvent.setLastModifiedAt(Instant.now());
        return EventMapper.toDTO(eventRepository.save(newEvent));
    }
    

    public Optional<EventDTO> getEventById(String id) {
        logger.info("Buscando evento com ID: {}", id);
        return eventRepository.findById(id).map(EventMapper::toDTO);
    }

    public List<EventDTO> getAllEvents() {
        logger.info("Buscando todos os eventos.");
        return eventRepository.findAll().stream()
                .map(EventMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Object updateEvent(String id, EventUpdateDTO eventUpdateDTO) {
        logger.info("Atualizando evento com ID: {}", id);
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    
        List<Event> conflictingEvents = eventRepository.findByDayAndLocation_Name(
            eventUpdateDTO.getDay(), eventUpdateDTO.getLocation().getName()
        );
    
        LocalTime newStart = eventUpdateDTO.getStartTime();
        LocalTime newEnd = eventUpdateDTO.getEndTime().plus(eventUpdateDTO.getCleanupDuration());
    
        for (Event conflict : conflictingEvents) {
            if (!conflict.getId().equals(id)) { // Evita verificar o próprio evento
                LocalTime existingStart = conflict.getStartTime();
                LocalTime existingEnd = conflict.getEndTime().plus(conflict.getCleanupDuration());
    
                boolean isOverlapping = !(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd));
    
                if (isOverlapping) {
                    if (eventUpdateDTO.getPriority().ordinal() > conflict.getPriority().ordinal()) {
                        conflict.setLocation(new EventLocation("A definir", conflict.getLocation().getFloor()));
                        eventRepository.save(conflict);
                    } else if (eventUpdateDTO.getPriority().ordinal() < conflict.getPriority().ordinal()) {
                        eventUpdateDTO.setLocation(new EventLocation("A definir", eventUpdateDTO.getLocation().getFloor()));
                    } else {
                        return new EventConflictDTO(
                            "Conflito de eventos com mesma prioridade. Escolha uma ação.",
                            EventMapper.toDTO(conflict)
                        );
                    }
                }
            }
        }
    
        EventMapper.updateEntityFromDTO(existingEvent, eventUpdateDTO);
        existingEvent.setLastModifiedAt(Instant.now());
    
        return EventMapper.toDTO(eventRepository.save(existingEvent));
    }
    
    public EventDTO resolveUpdateConflict(String conflictingEventId, EventUpdateDTO updatedEventDTO) {
        Event conflictingEvent = eventRepository.findById(conflictingEventId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento conflitante não encontrado"));
    
     
        conflictingEvent.setLocation(new EventLocation("A definir", conflictingEvent.getLocation().getFloor()));
        eventRepository.save(conflictingEvent);
    
        Event updatedEvent = eventRepository.findById(conflictingEventId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento atualizado não encontrado"));
    
        EventMapper.updateEntityFromDTO(updatedEvent, updatedEventDTO);
        updatedEvent.setLastModifiedAt(Instant.now());
    
        return EventMapper.toDTO(eventRepository.save(updatedEvent));
    }
    

    public void deleteEvent(String id) {
        logger.info("Deletando evento com ID: {}", id);
        if (!eventRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado");
        }
        eventRepository.deleteById(id);
    }
}