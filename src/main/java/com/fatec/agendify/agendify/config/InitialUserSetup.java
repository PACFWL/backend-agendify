package com.fatec.agendify.agendify.config;

import com.fatec.agendify.agendify.model.user.User;
import com.fatec.agendify.agendify.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Configuration
public class InitialUserSetup {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) { 
                List<User> users = List.of(
                    User.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Master")
                        .email("master@gmail.com")
                        .password(passwordEncoder.encode("master123")) 
                        .role(User.Role.MASTER)
                        .createdAt(Instant.now())
                        .lastModifiedAt(Instant.now())
                        .build(),
                    
                    User.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Requester")
                        .email("requester@gmail.com")
                        .password(passwordEncoder.encode("requester123")) 
                        .role(User.Role.REQUESTER)
                        .createdAt(Instant.now())
                        .lastModifiedAt(Instant.now())
                        .build(),

                    User.builder()
                        .id(UUID.randomUUID().toString())
                        .name("User")
                        .email("user@gmail.com")
                        .password(passwordEncoder.encode("user123")) 
                        .role(User.Role.USER)
                        .createdAt(Instant.now())
                        .lastModifiedAt(Instant.now())
                        .build()
                );

                userRepository.saveAll(users);
                System.out.println("Usuários iniciais criados!");
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
}
