package com.fatec.agendify.agendify.repository;
import java.time.LocalDate;
import com.fatec.agendify.agendify.model.Event;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByDayAndLocation_Name(LocalDate day, String locationName);
}