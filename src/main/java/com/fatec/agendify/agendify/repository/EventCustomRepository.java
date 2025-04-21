package com.fatec.agendify.agendify.repository;

import com.fatec.agendify.agendify.dto.event.EventDTO;
import com.fatec.agendify.agendify.dto.event.EventFilterDTO;
import java.util.List;

public interface EventCustomRepository {
    List<EventDTO> searchEvents(EventFilterDTO filter);
}
