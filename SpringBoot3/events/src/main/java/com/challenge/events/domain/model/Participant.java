package com.challenge.events.domain.model;

import com.challenge.events.domain.dto.ParticipantDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String CPF;

    public Participant(ParticipantDto participantDto) {
        this.name = participantDto.name();
        this.surname = participantDto.surname();
        this.CPF = participantDto.CPF();
    }
}