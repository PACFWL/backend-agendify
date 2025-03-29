package com.fatec.agendify.agendify.controller.eventApproval;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.agendify.agendify.model.event.Event;
import com.fatec.agendify.agendify.model.pendingEvent.PendingEvent;
import com.fatec.agendify.agendify.repository.EventRepository;
import com.fatec.agendify.agendify.repository.PendingEventRepository;
import com.fatec.agendify.agendify.service.pendingEvent.PendingEventService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/event-approval")
@RequiredArgsConstructor
public class EventApprovalController {
    private final PendingEventRepository pendingEventRepository;
    private final EventRepository eventRepository;

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Event> approveEvent(@PathVariable String id, @RequestParam int priority) {
        PendingEvent pendingEvent = pendingEventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pending event not found"));

        Event event = new Event();
        event.setName(pendingEvent.getName());
        event.setDescription(pendingEvent.getDescription());
        event.setDate(pendingEvent.getDate());
        event.setPriority(priority);

        pendingEventRepository.deleteById(id);
        eventRepository.save(event);

        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}/reject")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Void> rejectEvent(@PathVariable String id) {
        pendingEventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
