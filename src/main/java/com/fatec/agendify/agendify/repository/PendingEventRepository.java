package com.fatec.agendify.agendify.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.agendify.agendify.model.pendingEvent.PendingEvent;

@Repository
public interface PendingEventRepository extends MongoRepository<PendingEvent, String> {
    List<PendingEvent> findByEventRequesterId(String eventRequesterId);
}
