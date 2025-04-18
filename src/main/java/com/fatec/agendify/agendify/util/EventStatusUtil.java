package com.fatec.agendify.agendify.util;

import com.fatec.agendify.agendify.model.event.Event;
import com.fatec.agendify.agendify.model.event.EventStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class EventStatusUtil {

    public static EventStatus determineStatus(Event event) {
       
        if (event.getStatus() == EventStatus.CANCELADO ||
            event.getStatus() == EventStatus.ADIADO ||
            event.getStatus() == EventStatus.ATRASADO ||
            event.getStatus() == EventStatus.URGENTE ||
            event.getStatus() == EventStatus.INDEFINIDO ||
            event.getStatus() == EventStatus.APROVADO ||
            event.getStatus() == EventStatus.PENDENTE) {
            return event.getStatus();
        }

        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDate today = LocalDate.now(zoneId);
        LocalTime now = LocalTime.now(zoneId);
        LocalDate eventDay = event.getDay();

        LocalTime start = event.getStartTime();
        LocalTime end = event.getEndTime();
        LocalTime endWithCleanup = end.plus(event.getCleanupDuration());

        if (eventDay.isAfter(today)) {
            return EventStatus.PLANEJADO;
        } else if (eventDay.isBefore(today)) {
            return EventStatus.FINALIZADO;
        } else {
            if (now.isBefore(start)) {
                return EventStatus.EM_BREVE;
            } else if (!now.isBefore(start) && now.isBefore(end)) {
                return EventStatus.EM_ANDAMENTO;
            } else if (!now.isBefore(end) && now.isBefore(endWithCleanup)) {
                return EventStatus.EM_PAUSA;
            } else {
                return EventStatus.FINALIZADO;
            }
        }
    }
}
