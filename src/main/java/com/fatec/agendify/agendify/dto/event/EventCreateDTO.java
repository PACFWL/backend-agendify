package com.fatec.agendify.agendify.dto.event;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fatec.agendify.agendify.model.event.EventLocation;
import com.fatec.agendify.agendify.model.event.EventMode;
import com.fatec.agendify.agendify.model.event.EventPriority;
import com.fatec.agendify.agendify.model.event.EventStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCreateDTO {
    
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotNull(message = "O dia é obrigatório.")
    @FutureOrPresent(message = "O evento não pode ser criado no passado.")
    private LocalDate day;

    @NotNull(message = "O horário de início é obrigatório.")
    private LocalTime startTime;

    @NotNull(message = "O horário de término é obrigatório.")
    private LocalTime endTime;

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
   
    @NotEmpty(message = "Os recursos são obrigatórios.")
    private List<String> resourcesDescription; 

    @NotBlank(message = "A forma de divulgação é obrigatória.")
    private String disclosureMethod;

    @NotEmpty(message = "As disciplinas são obrigatórias.")
    private List<String> relatedSubjects;

    @NotBlank(message = "A estratégia de ensino é obrigatória.")
    private String teachingStrategy;

    @NotEmpty(message = "O(s) autore(s) são obrigatórios.")
    private List<String> authors;

    @NotEmpty(message = "O(s) curso(s) são obrigatórios.")
    private List<String> courses;

    @NotBlank(message = "O vínculo disciplinar é obrigatório.")
    private String disciplinaryLink;

    @NotNull(message = "O local é obrigatório.")
    private EventLocation location;

    @NotNull(message = "O estado do evento é obrigatório")
    private EventStatus status;

    @NotNull(message = "A prioridade é obrigatória")
    private EventPriority priority;

    @NotNull(message = "O tempo de intervalo é obrigatório.")
    private Duration cleanupDuration;

    private String observation;
}
