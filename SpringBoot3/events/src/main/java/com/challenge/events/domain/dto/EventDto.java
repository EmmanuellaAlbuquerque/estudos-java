package com.challenge.events.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record EventDto(

        @NotBlank(message = "O nome do Evento é obrigatório")
        String name,

        @NotNull(message =  "O número de vagas do Evento é obrigatório")
        @Positive(message = "O número de vagas do Evento deve ser maior que zero")
        int vacancies,

        @JsonProperty("starts_at")
        LocalDateTime startsAt,

        @JsonProperty("ends_at")
        LocalDateTime endsAt
)
{}