package com.fatec.agendify.agendify.model.pendingUser;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "pending_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingUser {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @NotBlank(message = "O nome é obrigatório.")
    private String name;
    
    @Indexed(unique = true)
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;
    
    @NotBlank(message = "A senha é obrigatória.")
    private String password; 
    
    private String role;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant lastModifiedAt;
}