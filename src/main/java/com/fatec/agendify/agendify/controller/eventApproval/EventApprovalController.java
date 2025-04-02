package com.fatec.agendify.agendify.controller.eventApproval;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.fatec.agendify.agendify.model.event.Event;

import com.fatec.agendify.agendify.service.pendingEvent.PendingEventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/event-approval")
@RequiredArgsConstructor
public class EventApprovalController {
    private final PendingEventService pendingEventService;

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Event> approveEvent(@PathVariable String id) {
        Event event = pendingEventService.approvePendingEvent(id);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}/reject")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Void> rejectEvent(@PathVariable String id) {
        pendingEventService.deletePendingEvent(id);
        return ResponseEntity.noContent().build();
    }
}

