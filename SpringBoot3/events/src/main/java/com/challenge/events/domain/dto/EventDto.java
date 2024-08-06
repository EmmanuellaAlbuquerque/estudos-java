package com.challenge.events.domain.dto;

import com.challenge.events.LocalDateTimeDeserializer;
import com.challenge.events.validator.ValidEventDates;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@ValidEventDates
public record EventDto(

        @NotBlank(message = "O nome do evento é obrigatório")
        @Size(max = 150, message = "O nome do evento deve ter no máximo 150 caracteres")
        String name,

        @NotNull(message =  "A quantidade de vagas disponíveis para o evento é obrigatória")
        @Positive(message = "O número de vagas do evento deve ser maior que zero")
        Integer vacancies,

        @NotNull(message = "A data de início do evento é obrigatória")
        @FutureOrPresent(message = "A data de início do evento deve ser uma data no presente ou no futuro")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonProperty("starts_at")
        LocalDateTime startsAt,

        @NotNull(message = "A data de fim do evento é obrigatória")
        @Future(message = "A data de fim do evento deve ser uma data no futuro")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonProperty("ends_at")
        LocalDateTime endsAt
)
{}