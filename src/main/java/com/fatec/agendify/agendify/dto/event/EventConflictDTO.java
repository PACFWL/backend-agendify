package com.fatec.agendify.agendify.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventConflictDTO {
    private String message;
    private EventDTO existingEvent;
}