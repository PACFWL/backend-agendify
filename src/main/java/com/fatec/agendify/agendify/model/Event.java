package com.fatec.agendify.agendify.model;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @Builder.Default
    private String id =  UUID.randomUUID().toString();

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "O dia é obrigatório.")
    private String day;

    @NotBlank(message = "O início de horário é obrigatório.")
    private String startSchedule;

    @NotBlank(message = "O término de horário é obrigatório.")
    private String lastSchedule;

    @NotBlank(message = "O horário é obrigatório.")
    private String time;

    @NotBlank(message = "O tema é obrigatório.")
    private String theme;

    @NotBlank(message = "O público-alvo é obrigatório.")
    private String targetAudience;

    @NotBlank(message = "O modalidade é obrigatório.")
    private String modality;

    @NotBlank(message = "O ambiente é obrigatório.")
    private String environment;

    @NotBlank(message = "O Organizador é obrigatório.")
    private String organizer;

    @NotBlank(message = "O recurso é obrigatório.")
    private String resources;

    @NotBlank(message = "A forma de divulgação é obrigatório.")
    private String formDisclosure;

    @NotBlank(message = "O observação é obrigatório.")
    private String observation;

    @NotBlank(message = "O disciplina é obrigatório.")
    private String disciplines;

    @NotBlank(message = "A estratégia de ensino é obrigatório.")
    private String teachingStrategy;
}
