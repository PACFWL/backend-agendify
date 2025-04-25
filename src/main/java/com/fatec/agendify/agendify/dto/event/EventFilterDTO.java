package com.fatec.agendify.agendify.dto.event;

import com.fatec.agendify.agendify.model.event.*;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class EventFilterDTO {
    private String name;
    private LocalDate day;
    private LocalDate startDay;
    private LocalDate endDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private String theme;
    private String targetAudience;
    private EventMode mode;
    private List<String> resourcesDescription;
    private String disclosureMethod;
    private List<String> relatedSubjects;
    private String teachingStrategy;
    private EventPriority priority;
    private EventStatus status;
    private AdministrativeEventStatus administrativeStatus;
    private String environment;
    private String organizer;
    private List<String> authors;
    private List<String> courses;
    private String disciplinaryLink;
    private String observation;
    private Duration cleanupDuration;
    private String locationName;
    private Floor locationFloor;
}
