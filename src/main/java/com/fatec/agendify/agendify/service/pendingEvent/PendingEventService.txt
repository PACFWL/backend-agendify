package com.fatec.agendify.agendify.service.pendingEvent;

import org.springframework.stereotype.Service;

import com.fatec.agendify.agendify.model.pendingEvent.PendingEvent;
import com.fatec.agendify.agendify.repository.PendingEventRepository;
import com.fatec.agendify.agendify.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PendingEventService {
    private final PendingEventRepository pendingEventRepository;
    private final UserService userService;

    public PendingEvent createPendingEvent(PendingEvent event) {
        event.setRequesterId(userService.getAuthenticatedUserId());
        return pendingEventRepository.save(event);
    }

    public List<PendingEvent> getAllPendingEvents() {
        return pendingEventRepository.findAll();
    }

    public List<PendingEvent> getRequesterPendingEvents() {
        return pendingEventRepository.findByRequesterId(userService.getAuthenticatedUserId());
    }

    public void deletePendingEvent(String id) {
        pendingEventRepository.deleteById(id);
    }
}
