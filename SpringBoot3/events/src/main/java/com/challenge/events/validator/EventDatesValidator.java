package com.challenge.events.validator;

import com.challenge.events.domain.dto.EventDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventDatesValidator implements ConstraintValidator<ValidEventDates, EventDto> {

    @Override
    public boolean isValid(EventDto event, ConstraintValidatorContext context) {
        if (event.startsAt() == null || event.endsAt() == null) {
            return true;
        }

        boolean isValid = event.endsAt().isAfter(event.startsAt());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A data de fim deve ser após à data de início")
                    .addPropertyNode("ends_at")
                    .addConstraintViolation();
        }

        return isValid;
    }
}