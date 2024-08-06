package com.challenge.events.domain.model;

import com.challenge.events.domain.dto.EventDto;
import com.challenge.events.enums.RegistrationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int vacancies;

    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    @Column(name = "ends_at", nullable = false)
    private LocalDateTime endsAt;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ParticipantRegistration> registeredParticipants = new ArrayList<>();

    public Event(EventDto eventDto) {
        this.name = eventDto.name();
        this.vacancies = eventDto.vacancies();
        this.startsAt = eventDto.startsAt();
        this.endsAt = eventDto.endsAt();
    }

    public void addParticipant(Participant participant, RegistrationStatus registrationStatus) {
        switch (registrationStatus) {
            case REGISTERED -> {
                if (this.vacancies > 0) {
                    addParticipantsToList(participant, registrationStatus);
                    this.vacancies -= 1;
                }
                else {
                    throw new RuntimeException("Todas as vagas foram preenchidas!");
                }
            }
            case RESERVED -> {
                addParticipantsToList(participant, registrationStatus);
            }
        }
    }

    public void removeParticipant(Participant participant) {
        Optional<ParticipantRegistration> participantRegistration = this.registeredParticipants.stream().filter(predicate -> predicate.getParticipant().equals(participant)).findFirst();

        if (participantRegistration.isPresent()) {
            this.registeredParticipants.remove(participantRegistration.get());
            this.vacancies += 1;
        }
        else  {
            throw new RuntimeException("Não foi possível desinscrever o participante.");
        }
    }

    private void addParticipantsToList(Participant participant, RegistrationStatus registrationStatus) {
        ParticipantRegistration participantRegistration = new ParticipantRegistration(this, participant, registrationStatus);
        this.registeredParticipants.add(participantRegistration);
    }
}