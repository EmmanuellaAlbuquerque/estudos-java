package com.challenge.events.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ParticipantDto(

        @NotBlank(message = "O nome do Participante é obrigatório")
        @Size(max = 50, message = "O nome do participante deve ter no máximo 50 caracteres")
        String name,

        @NotBlank(message = "O sobrenome do Participante é obrigatório")
        @Size(max = 50, message = "O sobrenome do participante deve ter no máximo 50 caracteres")
        String surname,

        @NotBlank(message = "O CPF do Participante é obrigatório")
        @Size(min = 11, max = 14, message = "O tamanho do CPF é inválido")
        @Pattern(regexp = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}-?[0-9]{2}", message = "O CPF informado é inválido")
        @JsonProperty("cpf")
        String CPF
)
{}