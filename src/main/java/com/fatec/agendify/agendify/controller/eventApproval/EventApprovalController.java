package com.fatec.agendify.agendify.controller.eventApproval;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.fatec.agendify.agendify.dto.event.EventConflictDTO;
import com.fatec.agendify.agendify.dto.event.EventDTO;

import com.fatec.agendify.agendify.service.pendingEvent.PendingEventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/event-approval")
@RequiredArgsConstructor
public class EventApprovalController {
    private final PendingEventService pendingEventService;

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Object> approveEvent(@PathVariable String id) {
        Object response = pendingEventService.approvePendingEvent(id);

        if (response instanceof EventDTO) {
            return ResponseEntity.ok(response);
        } else if (response instanceof EventConflictDTO) {
            return ResponseEntity.status(409).body(response);
        } else {
            return ResponseEntity.status(500).body("Erro inesperado");
        }
    }

    @DeleteMapping("/{id}/reject")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Void> rejectEvent(@PathVariable String id) {
        pendingEventService.deletePendingEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/resolve/{existingEventId}/{pendingEventId}")
    public ResponseEntity<EventDTO> resolvePendingConflict(
            @PathVariable String existingEventId,
            @PathVariable String pendingEventId) {
        EventDTO resolvedEvent = pendingEventService.resolvePendingEventConflict(existingEventId, pendingEventId);
        return ResponseEntity.ok(resolvedEvent);
    }
}

