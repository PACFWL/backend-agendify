package com.fatec.agendify.agendify.model;

public enum Floor {
    GROUND_FLOOR("Térreo"),
    FIRST_FLOOR("1º Andar"),
    SECOND_FLOOR("2º Andar"),
    THIRD_FLOOR("3º Andar");

    private final String description;

    Floor(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
