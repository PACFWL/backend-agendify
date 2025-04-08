package com.fatec.agendify.agendify.controller.pendingEvent;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.agendify.agendify.dto.pendingEvent.PendingEventCreateDTO;
import com.fatec.agendify.agendify.dto.pendingEvent.PendingEventDTO;
import com.fatec.agendify.agendify.dto.pendingEvent.PendingEventUpdateDTO;
import com.fatec.agendify.agendify.mapper.PendingEventMapper;
import com.fatec.agendify.agendify.model.pendingEvent.PendingEvent;
import com.fatec.agendify.agendify.service.pendingEvent.PendingEventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pending-events")
@RequiredArgsConstructor
public class PendingEventController {
    private final PendingEventService pendingEventService;

    @PostMapping
    @PreAuthorize("hasRole('REQUESTER')")
    public ResponseEntity<PendingEventDTO> createPendingEvent(@Valid @RequestBody PendingEventCreateDTO pendingEventCreateDTO) {
        PendingEvent pendingEvent = PendingEventMapper.toEntity(pendingEventCreateDTO);
        PendingEvent savedEvent = pendingEventService.createPendingEvent(pendingEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(PendingEventMapper.toDTO(savedEvent));
    }

    @GetMapping
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<List<PendingEventDTO>> getAllPendingEvents() {
        List<PendingEventDTO> dtos = pendingEventService.getAllPendingEvents().stream()
                .map(PendingEventMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
/** 
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('REQUESTER', 'MASTER')")
    public ResponseEntity<PendingEventDTO> getPendingEventById(@PathVariable String id) {
        PendingEvent event = pendingEventService.getPendingEventById(id);
        return ResponseEntity.ok(PendingEventMapper.toDTO(event));
    }
        **/

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('REQUESTER', 'MASTER')")
    public ResponseEntity<PendingEventDTO> updatePendingEvent(
            @PathVariable String id,
            @Valid @RequestBody PendingEventUpdateDTO updateDTO) {
        
        PendingEvent updated = pendingEventService.updatePendingEvent(id, updateDTO);
        return ResponseEntity.ok(PendingEventMapper.toDTO(updated));
    }

    @GetMapping("/my-requests")
    @PreAuthorize("hasRole('REQUESTER')")
    public ResponseEntity<List<PendingEventDTO>> getMyRequests() {
        List<PendingEventDTO> dtos = pendingEventService.getRequesterPendingEvents().stream()
                .map(PendingEventMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('REQUESTER', 'MASTER')")
    public ResponseEntity<Void> deletePendingEvent(@PathVariable String id) {
        pendingEventService.deletePendingEvent(id);
        return ResponseEntity.noContent().build();
    }
}
