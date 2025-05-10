package com.fatec.agendify.agendify.mapper;

import java.time.Instant;

import java.util.List;
import java.util.stream.Collectors;

import com.fatec.agendify.agendify.dto.pendingEvent.PendingEventCreateDTO;
import com.fatec.agendify.agendify.dto.pendingEvent.PendingEventDTO;
import com.fatec.agendify.agendify.dto.pendingEvent.PendingEventUpdateDTO;
import com.fatec.agendify.agendify.model.pendingEvent.PendingEvent;

public class PendingEventMapper {
    
    public static PendingEventDTO toDTO(PendingEvent pendingEvent) {
        return PendingEventDTO.builder()
                .id(pendingEvent.getId())
                .name(pendingEvent.getName())
                .day(pendingEvent.getDay())
                .startTime(pendingEvent.getStartTime())
                .endTime(pendingEvent.getEndTime())
                .theme(pendingEvent.getTheme())
                .targetAudience(pendingEvent.getTargetAudience())
                .mode(pendingEvent.getMode())
                .environment(pendingEvent.getEnvironment())
                .organizer(pendingEvent.getOrganizer())
                .resourcesDescription(pendingEvent.getResourcesDescription())
                .disclosureMethod(pendingEvent.getDisclosureMethod())
                .relatedSubjects(pendingEvent.getRelatedSubjects())
                .teachingStrategy(pendingEvent.getTeachingStrategy())
                .authors(pendingEvent.getAuthors())
                .courses(pendingEvent.getCourses())
                .disciplinaryLink(pendingEvent.getDisciplinaryLink())
                .location(pendingEvent.getLocation())
                .observation(pendingEvent.getObservation())
                .status(pendingEvent.getStatus())
                .administrativeStatus(pendingEvent.getAdministrativeStatus())
                .priority(pendingEvent.getPriority())
                .cleanupDuration(pendingEvent.getCleanupDuration()) 
                .eventRequesterId(pendingEvent.getEventRequesterId())
                .createdAt(pendingEvent.getCreatedAt())
                .lastModifiedAt(pendingEvent.getLastModifiedAt())
                .build();
    }

    public static PendingEvent toEntity(PendingEventDTO dto) {
        return PendingEvent.builder()
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
                .courses(dto.getCourses())
                .disciplinaryLink(dto.getDisciplinaryLink())
                .location(dto.getLocation())
                .observation(dto.getObservation())
                .status(dto.getStatus())
                .administrativeStatus(dto.getAdministrativeStatus())
                .priority(dto.getPriority())
                .cleanupDuration(dto.getCleanupDuration())
                .eventRequesterId(dto.getEventRequesterId())
                .build();
    }

    public static PendingEvent toEntity(PendingEventCreateDTO dto) {
        return PendingEvent.builder()
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
                .courses(dto.getCourses())
                .disciplinaryLink(dto.getDisciplinaryLink())
                .location(dto.getLocation())
                .observation(dto.getObservation())
                .status(dto.getStatus())
                .administrativeStatus(dto.getAdministrativeStatus())
                .priority(dto.getPriority())
                .cleanupDuration(dto.getCleanupDuration())
                .createdAt(Instant.now()) 
                .lastModifiedAt(Instant.now()) 
                .build();
    }

    public static void updateEntityFromDTO(PendingEvent pendingEvent, PendingEventUpdateDTO dto) {
        if (dto.getName() != null) pendingEvent.setName(dto.getName());
        if (dto.getDay() != null) pendingEvent.setDay(dto.getDay());
        if (dto.getStartTime() != null) pendingEvent.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) pendingEvent.setEndTime(dto.getEndTime());
        if (dto.getTheme() != null) pendingEvent.setTheme(dto.getTheme());
        if (dto.getTargetAudience() != null) pendingEvent.setTargetAudience(dto.getTargetAudience());
        if (dto.getMode() != null) pendingEvent.setMode(dto.getMode());
        if (dto.getEnvironment() != null) pendingEvent.setEnvironment(dto.getEnvironment());
        if (dto.getOrganizer() != null) pendingEvent.setOrganizer(dto.getOrganizer());
        if (dto.getResourcesDescription() != null) pendingEvent.setResourcesDescription(dto.getResourcesDescription());
        if (dto.getDisclosureMethod() != null) pendingEvent.setDisclosureMethod(dto.getDisclosureMethod());
        if (dto.getRelatedSubjects() != null) pendingEvent.setRelatedSubjects(dto.getRelatedSubjects());
        if (dto.getTeachingStrategy() != null) pendingEvent.setTeachingStrategy(dto.getTeachingStrategy());
        if (dto.getAuthors() != null) pendingEvent.setAuthors(dto.getAuthors());
        if (dto.getCourses() != null) pendingEvent.setCourses(dto.getCourses());
        if (dto.getDisciplinaryLink() != null) pendingEvent.setDisciplinaryLink(dto.getDisciplinaryLink());
        if (dto.getLocation() != null) pendingEvent.setLocation(dto.getLocation());
        if (dto.getObservation() != null) pendingEvent.setObservation(dto.getObservation());
        if (dto.getStatus() != null) pendingEvent.setStatus(dto.getStatus());
        if (dto.getAdministrativeStatus() != null) pendingEvent.setAdministrativeStatus(dto.getAdministrativeStatus());
        if (dto.getPriority() != null) pendingEvent.setPriority(dto.getPriority());    
        if (dto.getCleanupDuration() != null) pendingEvent.setCleanupDuration(dto.getCleanupDuration());
        if (dto.getEventRequesterId() != null) pendingEvent.setEventRequesterId(dto.getEventRequesterId());

        pendingEvent.setLastModifiedAt(Instant.now());
    }

    public static List<PendingEventDTO> toDTOList(List<PendingEvent> pendingEventList) {
        return pendingEventList.stream().map(PendingEventMapper::toDTO).collect(Collectors.toList());
    }
}
