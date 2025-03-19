package com.fatec.agendify.agendify.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotNull(message = "O dia é obrigatório.")
    private LocalDate day; // Alterado para LocalDate para representar a data corretamente

    @NotNull(message = "O início do horário é obrigatório.")
    private LocalTime startSchedule; // Alterado para LocalTime para representar o horário corretamente

    @NotNull(message = "O término do horário é obrigatório.")
    private LocalTime lastSchedule; // Alterado para LocalTime para representar o horário corretamente

    @NotNull(message = "O horário é obrigatório.")
    private LocalTime time; // Parece redundante com os outros horários, mas mantive

    @NotBlank(message = "O tema é obrigatório.")
    private String theme;

    @NotBlank(message = "O público-alvo é obrigatório.")
    private String targetAudience;

    @NotBlank(message = "A modalidade é obrigatória.")
    private String modality;

    @NotBlank(message = "O ambiente é obrigatório.")
    private String environment;

    @NotBlank(message = "O organizador é obrigatório.")
    private String organizer;

    @NotBlank(message = "Os recursos são obrigatórios.")
    private String resources;

    @NotBlank(message = "A forma de divulgação é obrigatória.")
    private String formDisclosure;

    private String observation; // Pode ser opcional, então removi @NotBlank

    @NotBlank(message = "A disciplina é obrigatória.")
    private String disciplines;

    @NotBlank(message = "A estratégia de ensino é obrigatória.")
    private String teachingStrategy;
}
