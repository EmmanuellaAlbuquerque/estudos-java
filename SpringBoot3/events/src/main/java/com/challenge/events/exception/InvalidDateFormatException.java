package com.challenge.events.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class InvalidDateFormatException extends RuntimeException {
    private final Map<String, String> errors;

    public InvalidDateFormatException(Map<String, String> errors) {
        this.errors = errors;
    }
}