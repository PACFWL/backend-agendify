package com.fatec.agendify.agendify.mapper;

import com.fatec.agendify.agendify.dto.event.EventCreateDTO;
import com.fatec.agendify.agendify.dto.event.EventDTO;
import com.fatec.agendify.agendify.dto.event.EventUpdateDTO;
import com.fatec.agendify.agendify.model.Event;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {

    public static EventDTO toDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .day(event.getDay())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .theme(event.getTheme())
                .targetAudience(event.getTargetAudience())
                .mode(event.getMode())
                .environment(event.getEnvironment())
                .organizer(event.getOrganizer())
                .resourcesDescription(event.getResourcesDescription())
                .disclosureMethod(event.getDisclosureMethod())
                .relatedSubjects(event.getRelatedSubjects())
                .teachingStrategy(event.getTeachingStrategy())
                .authors(event.getAuthors())
                .disciplinaryLink(event.getDisciplinaryLink())
                .location(event.getLocation())
                .observation(event.getObservation())
                .status(event.getStatus())
                .priority(event.getPriority())
                .cleanupDuration(event.getCleanupDuration()) 
                .createdAt(event.getCreatedAt())
                .lastModifiedAt(event.getLastModifiedAt())
                .build();
    }

    public static Event toEntity(EventCreateDTO dto) {
        return Event.builder()
                .name(dto.getName())
                .day(dto.getDay())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .theme(dto.getTheme())
                .targetAudience(dto.getTargetAudience())
                .mode(dto.getMode())
                .environment(dto.getEnvironment())
                .organizer(dto.getOrganizer())
                .resourcesDescription(dto.getResourcesDescription())
                .disclosureMethod(dto.getDisclosureMethod())
                .relatedSubjects(dto.getRelatedSubjects())
                .teachingStrategy(dto.getTeachingStrategy())
                .authors(dto.getAuthors())
                .disciplinaryLink(dto.getDisciplinaryLink())
                .location(dto.getLocation())
                .observation(dto.getObservation())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .cleanupDuration(dto.getCleanupDuration())
                .build();
    }

    public static void updateEntityFromDTO(Event event, EventUpdateDTO dto) {
        if (dto.getName() != null) event.setName(dto.getName());
        if (dto.getDay() != null) event.setDay(dto.getDay());
        if (dto.getStartTime() != null) event.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) event.setEndTime(dto.getEndTime());
        if (dto.getTheme() != null) event.setTheme(dto.getTheme());
        if (dto.getTargetAudience() != null) event.setTargetAudience(dto.getTargetAudience());
        if (dto.getMode() != null) event.setMode(dto.getMode());
        if (dto.getEnvironment() != null) event.setEnvironment(dto.getEnvironment());
        if (dto.getOrganizer() != null) event.setOrganizer(dto.getOrganizer());
        if (dto.getResourcesDescription() != null) event.setResourcesDescription(dto.getResourcesDescription());
        if (dto.getDisclosureMethod() != null) event.setDisclosureMethod(dto.getDisclosureMethod());
        if (dto.getRelatedSubjects() != null) event.setRelatedSubjects(dto.getRelatedSubjects());
        if (dto.getTeachingStrategy() != null) event.setTeachingStrategy(dto.getTeachingStrategy());
        if (dto.getAuthors() != null) event.setAuthors(dto.getAuthors());
        if (dto.getDisciplinaryLink() != null) event.setDisciplinaryLink(dto.getDisciplinaryLink());
        if (dto.getLocation() != null) event.setLocation(dto.getLocation());
        if (dto.getObservation() != null) event.setObservation(dto.getObservation());
        if (dto.getStatus() != null) event.setStatus(dto.getStatus());
        if (dto.getPriority() != null) event.setPriority(dto.getPriority());    
        if (dto.getCleanupDuration() != null) event.setCleanupDuration(dto.getCleanupDuration());

        event.setLastModifiedAt(Instant.now());
    }

    public static List<EventDTO> toDTOList(List<Event> eventList) {
        return eventList.stream().map(EventMapper::toDTO).collect(Collectors.toList());
    }
}
