package com.challenge.events.controller;

import com.challenge.events.domain.dto.EventDto;
import com.challenge.events.domain.model.Event;
import com.challenge.events.enums.RegistrationStatus;
import com.challenge.events.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto eventDto) {
        Event event = eventService.createEvent(eventDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(event.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{eventId}/participants/{participantId}/register")
    public ResponseEntity<?> registerParticipantToEvent(@PathVariable(value = "eventId") UUID eventId, @PathVariable(value = "participantId") UUID participantId ) {
        eventService.registerParticipantToEvent(eventId, participantId, RegistrationStatus.REGISTERED);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{eventId}/participants/{participantId}/reserve")
    public ResponseEntity<?> reserveParticipantToEvent(@PathVariable(value = "eventId") UUID eventId, @PathVariable(value = "participantId") UUID participantId ) {
        eventService.registerParticipantToEvent(eventId, participantId, RegistrationStatus.RESERVED);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{eventId}/participants/{participantId}/cancel")
    public ResponseEntity<?> cancelParticipantRegistrationOnEvent(@PathVariable(value = "eventId") UUID eventId, @PathVariable(value = "participantId") UUID participantId) {
        eventService.cancelParticipantRegistrationOnEvent(eventId, participantId);
        return ResponseEntity.ok().build();
    }
}