package com.fatec.agendify.agendify.dto.pendingUser;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingUserUpdateDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    private String password;

    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    @NotBlank(message = "O papel do usuário é obrigatório.")
    private String role;
}