package com.fatec.agendify.agendify.config;

import com.fatec.agendify.agendify.model.User;
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
                        .name("Teste A")
                        .email("abcdef@example.com")
                        .password(passwordEncoder.encode("admin123")) 
                        .role(User.Role.ADMIN)
                        .createdAt(Instant.now())
                        .lastModifiedAt(Instant.now())
                        .build(),
                    
                    User.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Teste B")
                        .email("teste@gmail.com")
                        .password(passwordEncoder.encode("Qwerty")) 
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
