package com.fatec.agendify.agendify.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fatec.agendify.agendify.model.event.Event;

@Repository
public interface EventRepository extends MongoRepository<Event, String>, EventCustomRepository {
    List<Event> findByDayAndLocation_Name(LocalDate day, String locationName);
}
