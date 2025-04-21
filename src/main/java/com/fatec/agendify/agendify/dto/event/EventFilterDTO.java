package com.fatec.agendify.agendify.dto.event;

import com.fatec.agendify.agendify.model.event.*;
import lombok.Data;
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
    private EventMode mode;
    private EventPriority priority;
    private EventStatus status;
    private AdministrativeEventStatus administrativeStatus;
    private String environment;
    private String organizer;
    private List<String> authors;
    private String locationName;
    private Floor locationFloor;
}
