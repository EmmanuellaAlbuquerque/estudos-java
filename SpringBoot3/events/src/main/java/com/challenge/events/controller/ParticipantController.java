package com.challenge.events.controller;

import com.challenge.events.domain.dto.ParticipantDto;
import com.challenge.events.domain.model.Participant;
import com.challenge.events.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}
