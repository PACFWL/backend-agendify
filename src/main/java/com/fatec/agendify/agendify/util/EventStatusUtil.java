package com.fatec.agendify.agendify.util;

import com.fatec.agendify.agendify.model.event.Event;
import com.fatec.agendify.agendify.model.event.EventStatus;
import com.fatec.agendify.agendify.model.event.AdministrativeEventStatus;

import java.time.*;

public class EventStatusUtil {

    public static EventStatus determineStatus(Event event) {
        AdministrativeEventStatus adminStatus = event.getAdministrativeStatus();

        switch (adminStatus) {
            case NORMAL:
            case APROVADO:
                return determineByTime(event);

            case CANCELADO:
            case RECUSADO:
                return EventStatus.FINALIZADO;

            case ADIADO:
            case INDEFINIDO:
                return EventStatus.INDETERMINADO; 

            case PENDENTE:
            case AGUARDANDO:
                return event.getStatus(); 

            case URGENTE:
               
                return determineByTime(event);

            case ATRASADO:
               
                LocalTime now = LocalTime.now(ZoneId.of("America/Sao_Paulo"));
                if (now.isAfter(event.getStartTime()) && now.isBefore(event.getEndTime())) {
                    return EventStatus.EM_ANDAMENTO;
                }
                return determineByTime(event);
        }
        return event.getStatus();
    }
    private static EventStatus determineByTime(Event event) {
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDate currentDate = LocalDate.now(zoneId);
        LocalTime currentTime = LocalTime.now(zoneId);

        LocalTime start = event.getStartTime();
        LocalTime end = event.getEndTime();
        Duration cleanup = event.getCleanupDuration();

        if (!event.getDay().equals(currentDate)) {
            return event.getDay().isAfter(currentDate)
                    ? EventStatus.PLANEJADO
                    : EventStatus.FINALIZADO;
        }
        if (currentTime.isBefore(start)) {
            long minutesUntilStart = Duration.between(currentTime, start).toMinutes();
            return minutesUntilStart <= 30 ? EventStatus.EM_BREVE : EventStatus.PLANEJADO;
        }
        if (!currentTime.isBefore(start) && currentTime.isBefore(end)) {
            return EventStatus.EM_ANDAMENTO;
        }
        LocalTime endWithCleanup = end.plus(cleanup);
        if (!currentTime.isBefore(end) && currentTime.isBefore(endWithCleanup)) {
            return EventStatus.EM_PAUSA;
        }
        return EventStatus.FINALIZADO;
    }
}
