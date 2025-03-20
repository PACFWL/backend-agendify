package com.fatec.agendify.agendify.dto.event;

import com.fatec.agendify.agendify.model.EventLocation;
import com.fatec.agendify.agendify.model.EventMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventUpdateDTO {

    private String name;
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime lastTime;
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
    private String observation;
}
