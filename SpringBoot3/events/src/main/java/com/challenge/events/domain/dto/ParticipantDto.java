package com.challenge.events.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record ParticipantDto(

        @NotBlank(message = "O nome do Participante é obrigatório")
        String name
)
{}