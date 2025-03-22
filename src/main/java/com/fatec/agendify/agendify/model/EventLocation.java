package com.fatec.agendify.agendify.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventLocation {
    private String name;  
    private Floor floor;  
}
