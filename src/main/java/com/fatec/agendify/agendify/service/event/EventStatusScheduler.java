package com.fatec.agendify.agendify.service.event;

import com.fatec.agendify.agendify.model.event.Event;
import com.fatec.agendify.agendify.model.event.EventStatus;
import com.fatec.agendify.agendify.repository.EventRepository;
import com.fatec.agendify.agendify.util.EventStatusUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class EventStatusScheduler {

    private static final Logger logger = LoggerFactory.getLogger(EventStatusScheduler.class);
    private final EventRepository eventRepository;

    public EventStatusScheduler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Scheduled(cron = "0 */10 * * * *", zone = "America/Sao_Paulo")
    public void updateEventStatuses() {
        logger.info("Iniciando verificação agendada de status de eventos...");

        List<Event> allEvents = eventRepository.findAll();
        for (Event event : allEvents) {
            EventStatus currentStatus = event.getStatus();
            EventStatus updatedStatus = EventStatusUtil.determineStatus(event);

            if (currentStatus != updatedStatus) {
                logger.info("Atualizando status do evento {} de {} para {}", event.getId(), currentStatus, updatedStatus);
                event.setStatus(updatedStatus);
                event.setLastModifiedAt(Instant.now());
                eventRepository.save(event);
            }
        }
        logger.info("Verificação de status concluída.");
    }
}
