package com.challenge.events.domain.repository;

import com.challenge.events.domain.model.Event;
import com.challenge.events.domain.model.ParticipantRegistration;
import com.challenge.events.enums.RegistrationStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipantRegistrationRepository extends JpaRepository<ParticipantRegistration, Long> {

    @Transactional
    Long deleteByEventIdAndParticipantId(
            UUID eventId,
            UUID participantId
    );

    List<ParticipantRegistration> findAllByEventIdAndRegistrationStatus(
            UUID eventId,
            RegistrationStatus registrationStatus
    );

    @Query("SELECT pr.event FROM ParticipantRegistration pr WHERE pr.participant.id = :participantId AND pr.registrationStatus = :registrationStatus")
    List<Event> findAllByParticipantIdAndRegistrationStatus(
            UUID participantId,
            RegistrationStatus registrationStatus
    );

    Optional<ParticipantRegistration> findByEventIdAndParticipantId(
            UUID eventId,
            UUID participantId
    );
}