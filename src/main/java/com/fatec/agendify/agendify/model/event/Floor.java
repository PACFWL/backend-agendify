package com.fatec.agendify.agendify.model.event;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Floor {
    GROUND_FLOOR("Térreo"),
    FIRST_FLOOR("1º Andar"),
    SECOND_FLOOR("2º Andar"),
    BLOCK_C_FLOOR("Bloco C");

    private final String description;

    Floor(String description) {
        this.description = description;
    }
    @JsonValue
    public String getDescription() {
        return description;
    }
}
