package com.fatec.agendify.agendify.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fatec.agendify.agendify.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(User.Role role);
}
