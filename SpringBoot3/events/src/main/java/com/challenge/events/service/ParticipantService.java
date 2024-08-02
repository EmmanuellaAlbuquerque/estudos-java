package com.challenge.events.service;

import com.challenge.events.domain.dto.ParticipantDto;
import com.challenge.events.domain.model.Participant;
import com.challenge.events.domain.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public Participant createParticipant(ParticipantDto participantDto) {
        Participant participant = new Participant(participantDto);
        return this.participantRepository.save(participant);
    }
}
