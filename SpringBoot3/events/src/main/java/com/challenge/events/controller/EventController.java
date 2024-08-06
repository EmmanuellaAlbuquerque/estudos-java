package com.challenge.events.controller;

import com.challenge.events.domain.dto.EventDto;
import com.challenge.events.domain.dto.Message;
import com.challenge.events.domain.model.Event;
import com.challenge.events.domain.model.ParticipantRegistration;
import com.challenge.events.enums.RegistrationStatus;
import com.challenge.events.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Registra um Evento")
    @PostMapping
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto eventDto) {
        Event event = eventService.createEvent(eventDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(event.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Registra um participante no Evento")
    @PostMapping("/{eventId}/participants/{participantId}/register")
    public ResponseEntity<?> registerParticipantToEvent(@PathVariable(value = "eventId") UUID eventId, @PathVariable(value = "participantId") UUID participantId ) {
        eventService.registerParticipantToEvent(eventId, participantId, RegistrationStatus.REGISTERED);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Reserva uma vaga no Evento")
    @PostMapping("/{eventId}/participants/{participantId}/reserve")
    public ResponseEntity<?> reserveParticipantToEvent(@PathVariable(value = "eventId") UUID eventId, @PathVariable(value = "participantId") UUID participantId ) {
        eventService.registerParticipantToEvent(eventId, participantId, RegistrationStatus.RESERVED);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Cancela inscrição no Evento")
    @DeleteMapping("/{eventId}/participants/{participantId}/cancel")
    public ResponseEntity<?> cancelParticipantRegistrationOnEvent(@PathVariable(value = "eventId") UUID eventId, @PathVariable(value = "participantId") UUID participantId) {
        eventService.cancelParticipantRegistrationOnEvent(eventId, participantId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Lista todos os participantes do Evento")
    @GetMapping("/{eventId}/participants")
    public ResponseEntity<List<ParticipantRegistration>> obtainAllRegisteredParticipants(@PathVariable(value = "eventId") UUID eventId) {
        List<ParticipantRegistration> participantRegistrations = eventService.getAllParticipantsByRegistrationStatus(eventId, RegistrationStatus.REGISTERED);

        return ResponseEntity.status(HttpStatus.OK).body(participantRegistrations);
    }

    @Operation(summary = "Valida um participante em um determinado evento")
    @GetMapping("/{eventId}/participants/{participantId}/valid")
    public ResponseEntity<Message> validateParticipantOnAnEvent(@PathVariable(value = "eventId") UUID eventId, @PathVariable(value = "participantId") UUID participantId) {
        return ResponseEntity.ok().body(eventService.validateParticipant(eventId, participantId));
    }

    @Operation(summary = "Converte uma reserva em uma inscrição de Evento")
    @PutMapping("/{eventId}/participants/{participantId}/convert")
    public ResponseEntity<?> convertReservationIntoRegistration(@PathVariable(value = "eventId") UUID eventId, @PathVariable(value = "participantId") UUID participantId) {
        eventService.convertReservationIntoRegistration(eventId, participantId);
        return ResponseEntity.ok().build();
    }
}