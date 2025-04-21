package com.fatec.agendify.agendify.repository.impl;

import com.fatec.agendify.agendify.dto.event.EventDTO;
import com.fatec.agendify.agendify.dto.event.EventFilterDTO;
import com.fatec.agendify.agendify.mapper.EventMapper;
import com.fatec.agendify.agendify.model.event.Event;
import com.fatec.agendify.agendify.repository.EventCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventCustomRepositoryImpl implements EventCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<EventDTO> searchEvents(EventFilterDTO filter) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (filter.getName() != null) {
            criteriaList.add(Criteria.where("name").regex(filter.getName(), "i")); 
        }
        if (filter.getDay() != null) {
            criteriaList.add(Criteria.where("day").is(filter.getDay()));
        }
        if (filter.getStartDay() != null && filter.getEndDay() != null) {
            criteriaList.add(Criteria.where("day").gte(filter.getStartDay()).lte(filter.getEndDay()));
        }
        if (filter.getStartTime() != null) {
            criteriaList.add(Criteria.where("startTime").gte(filter.getStartTime()));
        }
        if (filter.getEndTime() != null) {
            criteriaList.add(Criteria.where("endTime").lte(filter.getEndTime()));
        }
        if (filter.getMode() != null) {
            criteriaList.add(Criteria.where("mode").is(filter.getMode()));
        }
        if (filter.getPriority() != null) {
            criteriaList.add(Criteria.where("priority").is(filter.getPriority()));
        }
        if (filter.getStatus() != null) {
            criteriaList.add(Criteria.where("status").is(filter.getStatus()));
        }
        if (filter.getAdministrativeStatus() != null) {
            criteriaList.add(Criteria.where("administrativeStatus").is(filter.getAdministrativeStatus()));
        }
        if (filter.getEnvironment() != null) {
            criteriaList.add(Criteria.where("environment").regex(filter.getEnvironment(), "i"));
        }
        if (filter.getOrganizer() != null) {
            criteriaList.add(Criteria.where("organizer").regex(filter.getOrganizer(), "i"));
        }
        if (filter.getAuthors() != null && !filter.getAuthors().isEmpty()) {
            criteriaList.add(Criteria.where("authors").in(filter.getAuthors()));
        }
        if (filter.getLocationName() != null) {
            criteriaList.add(Criteria.where("location.name").regex(filter.getLocationName(), "i"));
        }
        if (filter.getLocationFloor() != null) {
            criteriaList.add(Criteria.where("location.floor").is(filter.getLocationFloor()));
        }

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        List<Event> events = mongoTemplate.find(query, Event.class);
        return events.stream()
                .map(EventMapper::toDTO)
                .toList();
    }
}
