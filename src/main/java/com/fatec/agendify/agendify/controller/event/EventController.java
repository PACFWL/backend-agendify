package com.fatec.agendify.agendify.controller.event;

import com.fatec.agendify.agendify.dto.event.EventConflictDTO;
import com.fatec.agendify.agendify.dto.event.EventCreateDTO;
import com.fatec.agendify.agendify.dto.event.EventDTO;
import com.fatec.agendify.agendify.dto.event.EventUpdateDTO;
import com.fatec.agendify.agendify.service.event.EventService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


@PostMapping
public ResponseEntity<?> createEvent(@RequestBody @Valid EventCreateDTO eventCreateDTO) {
    Object response = eventService.createEvent(eventCreateDTO);

    if (response instanceof EventConflictDTO) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    } else {
        return ResponseEntity.ok(response);
    }
}

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable String id) {
        Optional<EventDTO> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(
            @PathVariable String id,
            @RequestBody @Valid EventUpdateDTO eventUpdateDTO) {
        Object response = eventService.updateEvent(id, eventUpdateDTO);
    
        if (response instanceof EventConflictDTO) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Evento deletado com sucesso");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/resolve/{existingEventId}")
    public ResponseEntity<EventDTO> resolveConflict(
            @PathVariable String existingEventId,
            @RequestBody @Valid EventCreateDTO newEventDTO) {
        return ResponseEntity.ok(eventService.resolveEventConflict(existingEventId, newEventDTO));
    }

    @PostMapping("/resolve-update/{conflictingEventId}")
    public ResponseEntity<EventDTO> resolveUpdateConflict(
            @PathVariable String conflictingEventId,
            @RequestBody @Valid EventUpdateDTO updatedEventDTO) {
    
        String updatedEventId = updatedEventDTO.getId();
    
        return ResponseEntity.ok(eventService.resolveUpdateConflict(conflictingEventId, updatedEventId, updatedEventDTO));
    } 
}

