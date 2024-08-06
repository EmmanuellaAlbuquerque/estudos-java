package com.challenge.events.exception;

import java.util.Map;

public class InvalidDateFormatException extends BaseFormatException {

    public InvalidDateFormatException(Map<String, String> errors) {
        super(errors);
    }
}