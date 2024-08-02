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

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonProperty("starts_at")
        LocalDateTime startsAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonProperty("ends_at")
        LocalDateTime endsAt
)
{}