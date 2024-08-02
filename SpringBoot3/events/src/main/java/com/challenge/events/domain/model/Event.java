package com.challenge.events.domain.model;

import com.challenge.events.domain.dto.EventDto;
import com.challenge.events.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<ParticipantRegistration> registeredParticipants = new ArrayList<>();

    public Event(EventDto eventDto) {
        this.name = eventDto.name();
        this.vacancies = eventDto.vacancies();
        this.startsAt = eventDto.startsAt();
        this.endsAt = eventDto.endsAt();
    }

    public void addParticipant(Participant participant, RegistrationStatus registrationStatus) {
        // TODO: Lógica de não existir mais vagas

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
            case CANCELLED -> {
                // TODO: canceled case
            }
        }
    }

    private void addParticipantsToList(Participant participant, RegistrationStatus registrationStatus) {
        ParticipantRegistration participantRegistration = new ParticipantRegistration(this, participant, registrationStatus);
        this.registeredParticipants.add(participantRegistration);
    }
}