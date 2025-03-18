package com.fatec.agendify.agendify.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;
    
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    @NotBlank(message = "O papel do usuário é obrigatório.")
    private String role;
}