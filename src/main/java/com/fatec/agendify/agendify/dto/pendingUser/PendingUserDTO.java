package com.fatec.agendify.agendify.dto.pendingUser;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingUserDTO {

    private String id;
    private String name;
    private String email;
    private String role;
}