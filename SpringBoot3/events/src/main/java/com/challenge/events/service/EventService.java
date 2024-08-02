package com.challenge.events.service;

import com.challenge.events.domain.dto.EventDto;
import com.challenge.events.domain.model.Event;
import com.challenge.events.domain.model.Participant;
import com.challenge.events.domain.repository.EventRepository;
import com.challenge.events.domain.repository.ParticipantRepository;
import com.challenge.events.enums.RegistrationStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    public EventService(EventRepository eventRepository, ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
    }

    public Event createEvent(EventDto eventDto) {
        Event event = new Event(eventDto);
        return this.eventRepository.save(event);
    }

    public void registerParticipantToEvent(UUID event_id, UUID participant_id, RegistrationStatus registrationStatus) {
        Event event = eventRepository.findById(event_id).orElseThrow(() -> new RuntimeException("Evento não encontrado!"));

        Participant participant = participantRepository.findById(participant_id).orElseThrow(() -> new RuntimeException("Participante não encontrado!"));

        event.addParticipant(participant, registrationStatus);

        try {
            eventRepository.save(event);
        }
        catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Participante já foi registrado nesse evento!");
        }
    }
}