package com.challenge.events.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class BaseFormatException extends RuntimeException {
    private final Map<String, String> errors;

    public BaseFormatException(Map<String, String> errors) {
        this.errors = errors;
    }
}