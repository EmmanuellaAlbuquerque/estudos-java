package com.challenge.events.service;

import com.challenge.events.domain.dto.ParticipantDto;
import com.challenge.events.domain.model.Event;
import com.challenge.events.domain.model.Participant;
import com.challenge.events.domain.repository.ParticipantRegistrationRepository;
import com.challenge.events.domain.repository.ParticipantRepository;
import com.challenge.events.enums.RegistrationStatus;
import com.challenge.events.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    private ParticipantRegistrationRepository participantRegistrationRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public Participant createParticipant(ParticipantDto participantDto) {
        Participant participant = new Participant(participantDto);
        return this.participantRepository.save(participant);
    }

    public ParticipantDto findParticipantById(UUID participantId) {
        Participant participant = participantRepository.findById(participantId).orElseThrow(() -> new NotFoundException(Map.of("error", "Participante não encontrado!")));

        return new ParticipantDto(participant.getName(), participant.getSurname(), participant.getCPF());
    }

    public List<Event> findAllRegisteredEventsFromParticipant(UUID participantId) {
        Participant participant = participantRepository.findById(participantId).orElseThrow(() -> new NotFoundException(Map.of("error", "Participante não encontrado!")));

        return participantRegistrationRepository
                .findAllByParticipantIdAndRegistrationStatus(participantId, RegistrationStatus.REGISTERED);
    }
}
