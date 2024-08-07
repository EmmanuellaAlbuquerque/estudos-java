package com.challenge.events.exception;

import java.util.Map;

public class NotFoundException extends BaseFormatException {
    public NotFoundException(Map<String, String> errors) {
        super(errors);
    }
}