package com.fatec.agendify.agendify.controller.pendingEvent;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.agendify.agendify.model.pendingEvent.PendingEvent;
import com.fatec.agendify.agendify.service.pendingEvent.PendingEventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pending-events")
@RequiredArgsConstructor
public class PendingEventController {
    private final PendingEventService pendingEventService;

    @PostMapping
    @PreAuthorize("hasRole('REQUESTER')")
    public ResponseEntity<PendingEvent> createPendingEvent(@RequestBody PendingEvent event) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pendingEventService.createPendingEvent(event));
    }

    @GetMapping
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<List<PendingEvent>> getAllPendingEvents() {
        return ResponseEntity.ok(pendingEventService.getAllPendingEvents());
    }

    @GetMapping("/my-requests")
    @PreAuthorize("hasRole('REQUESTER')")
    public ResponseEntity<List<PendingEvent>> getMyRequests() {
        return ResponseEntity.ok(pendingEventService.getRequesterPendingEvents());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('REQUESTER')")
    public ResponseEntity<Void> deletePendingEvent(@PathVariable String id) {
        pendingEventService.deletePendingEvent(id);
        return ResponseEntity.noContent().build();
    }
}
