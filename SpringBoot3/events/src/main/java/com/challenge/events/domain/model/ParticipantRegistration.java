package com.challenge.events.domain.model;

import com.challenge.events.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "participant_registration",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"participant_id", "event_id"}
        )
)
public class ParticipantRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    private LocalDateTime registrationDate;

    private RegistrationStatus registrationStatus;

    public ParticipantRegistration(Event event, Participant participant, RegistrationStatus registrationStatus) {
        this.event = event;
        this.participant = participant;
        this.registrationDate = LocalDateTime.now();
        this.registrationStatus = registrationStatus;
    }
}
