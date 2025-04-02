package com.fatec.agendify.agendify.service.pendingEvent;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatec.agendify.agendify.model.event.Event;
import com.fatec.agendify.agendify.model.event.EventStatus;
import com.fatec.agendify.agendify.model.pendingEvent.PendingEvent;
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

    public PendingEvent createPendingEvent(PendingEvent event) {
        event.setEventRequesterId(userService.getAuthenticatedUserId());
        return pendingEventRepository.save(event);
    }

    public List<PendingEvent> getAllPendingEvents() {
        return pendingEventRepository.findAll();
    }

    public List<PendingEvent> getRequesterPendingEvents() {
        return pendingEventRepository.findByEventRequesterId(userService.getAuthenticatedUserId());
    }

    public void deletePendingEvent(String id) {
        pendingEventRepository.deleteById(id);
    }

    public Event approvePendingEvent(String pendingEventId) {
        PendingEvent pendingEvent = pendingEventRepository.findById(pendingEventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pending event not found"));

        Event event = Event.builder()
                .name(pendingEvent.getName())
                .day(pendingEvent.getDay())
                .startTime(pendingEvent.getStartTime())
                .endTime(pendingEvent.getEndTime())
                .theme(pendingEvent.getTheme())
                .targetAudience(pendingEvent.getTargetAudience())
                .mode(pendingEvent.getMode())
                .environment(pendingEvent.getEnvironment())
                .organizer(pendingEvent.getOrganizer())
                .resourcesDescription(pendingEvent.getResourcesDescription())
                .disclosureMethod(pendingEvent.getDisclosureMethod())
                .relatedSubjects(pendingEvent.getRelatedSubjects())
                .teachingStrategy(pendingEvent.getTeachingStrategy())
                .authors(pendingEvent.getAuthors())
                .courses(pendingEvent.getCourses())
                .disciplinaryLink(pendingEvent.getDisciplinaryLink())
                .location(pendingEvent.getLocation())
                .observation(pendingEvent.getObservation())
                .status(EventStatus.APROVADO) 
                .priority(pendingEvent.getPriority())
                .cleanupDuration(pendingEvent.getCleanupDuration())
                .build();

        eventRepository.save(event);
        pendingEventRepository.deleteById(pendingEventId);
        return event;
    }
}

