package com.challenge.events.controller;

import com.challenge.events.domain.dto.ParticipantDto;
import com.challenge.events.domain.model.Event;
import com.challenge.events.domain.model.Participant;
import com.challenge.events.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Operation(summary = "Registra um participante")
    @PostMapping
    public ResponseEntity<ParticipantDto> createParticipant(@Valid @RequestBody ParticipantDto participantDto) {
        Participant participant = participantService.createParticipant(participantDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(participant.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Obtém detalhes de um Participante")
    @GetMapping("/{participantId}")
    public ResponseEntity<ParticipantDto> obtainParticipantDetails(@PathVariable(value = "participantId") UUID participantId) {
        ParticipantDto participantDto = participantService.findParticipantById(participantId);
        return ResponseEntity.ok().body(participantDto);
    }

    @Operation(summary = "Lista os Eventos que o Participante está registrado")
    @GetMapping("/{participantId}/events")
    public ResponseEntity<List<Event>> obtainAllRegisteredEventsFromParticipant(@PathVariable(value = "participantId") UUID participantId) {
        List<Event> registeredEvents = participantService.findAllRegisteredEventsFromParticipant(participantId);
        return ResponseEntity.ok().body(registeredEvents);
    }
}
