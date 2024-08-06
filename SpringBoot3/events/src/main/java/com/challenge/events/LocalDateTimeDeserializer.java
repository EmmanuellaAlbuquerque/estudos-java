package com.challenge.events;

import com.challenge.events.exception.InvalidDateFormatException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    public LocalDateTimeDeserializer() {
        this(null);
    }

    public LocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();

        try {
            return LocalDateTime.parse(date);
        } catch (DateTimeParseException exception) {
            throw new InvalidDateFormatException(Map.of(jsonParser.currentName(), "A data deve ser válida (exemplo de formato válido: yyyy-MM-dd'T'HH:mm:ss)"));
        }
    }
}