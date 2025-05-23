package com.fatec.agendify.agendify.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fatec.agendify.agendify.model.event.AdministrativeEventStatus;
import com.fatec.agendify.agendify.model.event.EventLocation;
import com.fatec.agendify.agendify.model.event.EventMode;
import com.fatec.agendify.agendify.model.event.EventPriority;
import com.fatec.agendify.agendify.model.event.EventStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {

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
    private List<String> authors;//
    private List<String> courses;
    private String disciplinaryLink;
    private EventLocation location;
    private String observation;
    private EventStatus status;
    private AdministrativeEventStatus administrativeStatus;
    private EventPriority priority;
    private Duration cleanupDuration;
    private Instant createdAt;
    private Instant lastModifiedAt;

    @JsonIgnore
    public Instant getCreatedAt() {
        return createdAt;
    }

    @JsonIgnore
    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }
   
    @JsonProperty("createdAt")
    public ZonedDateTime getCreatedAtZoned() {
        return createdAt != null ? createdAt.atZone(ZoneId.of("America/Sao_Paulo")) : null;
    }

    @JsonProperty("lastModifiedAt")
    public ZonedDateTime getLastModifiedAtZoned() {
        return lastModifiedAt != null ? lastModifiedAt.atZone(ZoneId.of("America/Sao_Paulo")) : null;
    }
}
