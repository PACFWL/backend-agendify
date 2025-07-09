package com.fatec.agendify.agendify.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fatec.agendify.agendify.model.pendingUser.PendingUser;

import java.util.Optional;

public interface PendingUserRepository extends MongoRepository<PendingUser, String> {
    Optional<PendingUser> findByEmail(String email);
    boolean existsByEmail(String email);
}