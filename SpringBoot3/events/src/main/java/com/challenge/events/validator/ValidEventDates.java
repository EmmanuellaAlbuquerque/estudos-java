package com.challenge.events.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;

/**
 * Anotação personalizada para validação das datas de um Evento na API.
 * Bean Validation (JSR 380)
 * @version 1.0
 */
@Constraint(validatedBy = EventDatesValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEventDates {
    String message() default "A data de início deve ser anterior à data de fim";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}