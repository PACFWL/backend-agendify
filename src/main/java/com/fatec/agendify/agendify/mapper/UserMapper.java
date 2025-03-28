package com.fatec.agendify.agendify.mapper;

import com.fatec.agendify.agendify.dto.user.UserCreateDTO;
import com.fatec.agendify.agendify.dto.user.UserDTO;
import com.fatec.agendify.agendify.dto.user.UserUpdateDTO;
import com.fatec.agendify.agendify.model.user.User;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public static User toEntity(UserCreateDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(User.Role.valueOf(dto.getRole().toUpperCase()))
                .createdAt(Instant.now())
                .build();
    }

    public static void updateEntityFromDTO(User user, UserUpdateDTO dto) {
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getRole() != null) user.setRole(User.Role.valueOf(dto.getRole().toUpperCase()));
        user.setLastModifiedAt(Instant.now());
    }

    public static List<UserDTO> toDTOList(List<User> userList) {
        return userList.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }
}