package com.challenge.events.domain.model;

import com.challenge.events.domain.dto.EventDto;
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

    public Event(EventDto eventDto) {
        this.name = eventDto.name();
        this.vacancies = eventDto.vacancies();
        this.startsAt = eventDto.startsAt();
        this.endsAt = eventDto.endsAt();
    }
}