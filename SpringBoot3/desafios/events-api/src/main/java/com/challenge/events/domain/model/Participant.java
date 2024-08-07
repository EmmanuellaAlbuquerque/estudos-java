package com.challenge.events.domain.model;

import com.challenge.events.domain.dto.ParticipantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    private String CPF;

    @OneToMany(mappedBy = "participant")
    @JsonIgnore
    private List<ParticipantRegistration> registeredParticipants;

    public Participant(ParticipantDto participantDto) {
        this.name = participantDto.name();
        this.surname = participantDto.surname();
        this.CPF = participantDto.CPF();
    }
}