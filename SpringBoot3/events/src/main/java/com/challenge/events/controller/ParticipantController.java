package com.challenge.events.controller;

import com.challenge.events.domain.dto.ParticipantDto;
import com.challenge.events.domain.model.Event;
import com.challenge.events.domain.model.Participant;
import com.challenge.events.domain.model.ParticipantRegistration;
import com.challenge.events.domain.repository.ParticipantRegistrationRepository;
import com.challenge.events.domain.repository.ParticipantRepository;
import com.challenge.events.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<ParticipantDto> createParticipant(@Valid @RequestBody ParticipantDto participantDto) {
        Participant participant = participantService.createParticipant(participantDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(participant.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{participantId}/events")
    public ResponseEntity<List<Event>> obtainAllRegisteredEventsFromParticipant(@PathVariable(value = "participantId") UUID participantId) {
        List<Event> registeredEvents = participantService.findAllRegisteredEventsFromParticipant(participantId);
        return ResponseEntity.ok().body(registeredEvents);
    }
}
