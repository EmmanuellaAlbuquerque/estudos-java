package com.challenge.events.domain.repository;

import com.challenge.events.domain.model.Participant;
import com.challenge.events.domain.model.ParticipantRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    Optional<Participant> findByCPF(String CPF);
}