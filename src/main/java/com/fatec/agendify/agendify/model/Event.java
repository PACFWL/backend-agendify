package com.fatec.agendify.agendify.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fatec.agendify.agendify.validation.ValidEventLocation;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidEventLocation
public class Event {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotNull(message = "O dia é obrigatório.")
    private LocalDate day;

    @NotNull(message = "O início do horário é obrigatório.")
    private LocalTime startTime;

    @NotNull(message = "O término do horário é obrigatório.")
    private LocalTime lastTime; 

    @NotBlank(message = "O tema é obrigatório.")
    private String theme;

    @NotBlank(message = "O público-alvo é obrigatório.")
    private String targetAudience;

    @NotNull(message = "A modalidade é obrigatória.")
    private EventMode mode;

    @NotBlank(message = "O ambiente é obrigatório.")
    private String environment;

    @NotBlank(message = "O organizador é obrigatório.")
    private String organizer;

    @NotBlank(message = "Os recursos são obrigatórios.")
    private List<String> resourcesDescription;

    @NotBlank(message = "A forma de divulgação é obrigatória.")
    private String disclosureMethod;

    @NotBlank(message = "A disciplina é obrigatória.")
    private List<String> relatedSubjects;

    @NotBlank(message = "A estratégia de ensino é obrigatória.")
    private String teachingStrategy;

    @NotBlank(message = "O(s) autore(s) são obrigatórios.")
    private List<String> authors;

    @NotBlank(message = "O vínculo disciplinar são obrigatórios.")
    private String disciplinaryLink;

    @NotNull(message = "O local é obrigatório.")
    private EventLocation location;
    
    private Instant createdAt; 
    
    @LastModifiedDate
    private Instant lastModifiedAt;

    private String observation; 
}