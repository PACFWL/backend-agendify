package com.fatec.agendify.agendify.service.pendingUser;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.agendify.agendify.dto.pendingUser.PendingUserCreateDTO;
import com.fatec.agendify.agendify.dto.pendingUser.PendingUserUpdateDTO;
import com.fatec.agendify.agendify.dto.user.UserCreateDTO;
import com.fatec.agendify.agendify.dto.user.UserDTO;
import com.fatec.agendify.agendify.mapper.UserMapper;
import com.fatec.agendify.agendify.model.pendingUser.PendingUser;
import com.fatec.agendify.agendify.model.user.User;
import com.fatec.agendify.agendify.repository.PendingUserRepository;
import com.fatec.agendify.agendify.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class PendingUserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PendingUserRepository pendingUserRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PendingUser getPendingUserById(String id) {
        return pendingUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário pendente não encontrado"));
    }

    public PendingUser createPendingUser(PendingUserCreateDTO pendingUserDTO) {
    if (pendingUserRepository.existsByEmail(pendingUserDTO.getEmail()) || userRepository.findByEmail(pendingUserDTO.getEmail()).isPresent()) {
        throw new RuntimeException("E-mail já em uso.");
    }

    PendingUser pending = PendingUser.builder()
            .name(pendingUserDTO.getName())
            .email(pendingUserDTO.getEmail())
            .password(passwordEncoder.encode(pendingUserDTO.getPassword()))
            .role(pendingUserDTO.getRole().toUpperCase())
            .createdAt(Instant.now())
            .build();

    return pendingUserRepository.save(pending);
}


public List<PendingUser> listPendingUsers() {
    return pendingUserRepository.findAll();
}

public PendingUser updatePendingUser(String id, PendingUserUpdateDTO pendingUserUpdateDTO) {
    PendingUser pending = pendingUserRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário pendente não encontrado"));

    pending.setName(pendingUserUpdateDTO.getName());
    pending.setEmail(pendingUserUpdateDTO.getEmail());

    if (pendingUserUpdateDTO.getPassword() != null && !pendingUserUpdateDTO.getPassword().trim().isEmpty()) {
        pending.setPassword(passwordEncoder.encode(pendingUserUpdateDTO.getPassword()));
    }

    pending.setRole(pendingUserUpdateDTO.getRole().toUpperCase());
    pending.setLastModifiedAt(Instant.now());

    return pendingUserRepository.save(pending);
}

public void rejectPendingUser(String id) {
    pendingUserRepository.deleteById(id);
}

public UserDTO approvePendingUser(String id) {
    PendingUser pending = pendingUserRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário pendente não encontrado"));

    UserCreateDTO dto = UserCreateDTO.builder()
            .name(pending.getName())
            .email(pending.getEmail())
            .password(pending.getPassword()) 
            .role(pending.getRole())
            .build();

    User user = User.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .password(dto.getPassword()) 
            .role(User.Role.valueOf(dto.getRole().toUpperCase()))
            .createdAt(Instant.now())
            .build();

    pendingUserRepository.deleteById(id);
    return UserMapper.toDTO(userRepository.save(user));
}


    public void deletePendingUser(String id) {
        if (!pendingUserRepository.existsById(id)) {
            throw new RuntimeException("Usuário pendente não encontrado");
        }
        pendingUserRepository.deleteById(id);
    }
}
