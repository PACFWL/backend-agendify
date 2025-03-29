package com.fatec.agendify.agendify.dto.pendingEvent;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fatec.agendify.agendify.model.event.EventLocation;
import com.fatec.agendify.agendify.model.event.EventMode;
import com.fatec.agendify.agendify.model.event.EventPriority;
import com.fatec.agendify.agendify.model.event.EventStatus;
import com.fatec.agendify.agendify.validation.ValidEventLocation;
import java.time.Duration;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidEventLocation
public class PendingEventDTO {

    private String id;
    private String name;
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime; 
    private String theme;
    private String targetAudience;
    private EventMode mode;
    private String environment;
    private String organizer;
    private List<String> resourcesDescription;
    private String disclosureMethod;
    private List<String> relatedSubjects;
    private String teachingStrategy;
    private List<String> authors;
    private String disciplinaryLink;
    private EventLocation location;
    private Instant createdAt;     
    private Instant lastModifiedAt;
    private EventStatus status;
    private EventPriority priority;
    private Duration cleanupDuration;
    private String observation;
    private String requesterId;
}