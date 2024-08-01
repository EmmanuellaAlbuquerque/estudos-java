package com.challenge.events.service;

import com.challenge.events.domain.dto.EventDto;
import com.challenge.events.domain.model.Event;
import com.challenge.events.domain.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(EventDto eventDto) {
        Event event = new Event(eventDto);
        return this.eventRepository.save(event);
    }
}