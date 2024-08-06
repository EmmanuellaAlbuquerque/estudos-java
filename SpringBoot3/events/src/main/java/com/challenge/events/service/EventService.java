package com.challenge.events.service;

import com.challenge.events.domain.dto.EventDto;
import com.challenge.events.domain.dto.Message;
import com.challenge.events.domain.model.Event;
import com.challenge.events.domain.model.Participant;
import com.challenge.events.domain.model.ParticipantRegistration;
import com.challenge.events.domain.repository.EventRepository;
import com.challenge.events.domain.repository.ParticipantRegistrationRepository;
import com.challenge.events.domain.repository.ParticipantRepository;
import com.challenge.events.enums.RegistrationStatus;
import com.challenge.events.exception.BaseFormatException;
import com.challenge.events.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantRegistrationRepository participantRegistrationRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(EventDto eventDto) {
        Event event = new Event(eventDto);
        return this.eventRepository.save(event);
    }

    public void registerParticipantToEvent(UUID eventId, UUID participantId, RegistrationStatus registrationStatus) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(Map.of("error", "Evento não encontrado!")));
        Participant participant = participantRepository.findById(participantId).orElseThrow(() -> new NotFoundException(Map.of("error", "Participante não encontrado!")));

        LocalDateTime today = LocalDateTime.now();

        if (today.isBefore(event.getStartsAt())) {
            event.addParticipant(participant, registrationStatus);
        }
        else {
            throw new BaseFormatException(Map.of("error", "O período de inscrição já acabou!"));
        }

        try {
            eventRepository.save(event);
        }
        catch (DataIntegrityViolationException exception) {
            throw new BaseFormatException(Map.of("error", "Participante já foi registrado nesse evento!"));
        }
    }

    public void cancelParticipantRegistrationOnEvent(UUID eventId, UUID participantId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(Map.of("error", "Evento não encontrado!")));
        Participant participant = participantRepository.findById(participantId).orElseThrow(() -> new NotFoundException(Map.of("error", "Participante não encontrado!")));

        if (participantRegistrationRepository.deleteByEventIdAndParticipantId(eventId, participantId) != 1 ) {
            throw new BaseFormatException(Map.of("error", "Não foi possível desinscrever o participante."));
        }

        event.setVacancies(event.getVacancies() + 1);
        eventRepository.save(event);
    }

    public List<ParticipantRegistration> getAllParticipantsByRegistrationStatus(UUID eventId, RegistrationStatus registrationStatus) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(Map.of("error", "Evento não encontrado!")));

        return participantRegistrationRepository.findAllByEventIdAndRegistrationStatus(eventId, registrationStatus);
    }

    public Message validateParticipant(UUID eventId, UUID participantId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(Map.of("error", "Evento não encontrado!")));
        Participant participant = participantRepository.findById(participantId).orElseThrow(() -> new NotFoundException(Map.of("error", "Participante não encontrado!")));

        Optional<ParticipantRegistration> participantRegistration = participantRegistrationRepository.findByEventIdAndParticipantId(eventId, participantId);

        if (participantRegistration.isPresent()) {
            return new Message("Participante validado com sucesso!");
        }

        return new Message("Não foi possível validar o participante no Evento!");
    }

    public void convertReservationIntoRegistration(UUID eventId, UUID participantId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(Map.of("error", "Evento não encontrado!")));
        Participant participant = participantRepository.findById(participantId).orElseThrow(() -> new NotFoundException(Map.of("error", "Participante não encontrado!")));

        Optional<ParticipantRegistration> participantRegistration = participantRegistrationRepository.findByEventIdAndParticipantId(eventId, participantId);

        if (participantRegistration.isPresent()) {
            ParticipantRegistration registration = participantRegistration.get();
            if (registration.getRegistrationStatus() != RegistrationStatus.REGISTERED) {
                registration.setRegistrationStatus(RegistrationStatus.REGISTERED);
                participantRegistrationRepository.save(registration);
            }
            else {
                throw new BaseFormatException(Map.of("error", "Participante já está registrado!"));
            }
        }
        else {
            throw new BaseFormatException(Map.of("error", "Reserva não encontrada!"));
        }
    }
}