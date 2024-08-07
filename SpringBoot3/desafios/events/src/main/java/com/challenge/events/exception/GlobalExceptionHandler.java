package com.challenge.events.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationsExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(camelToSnakeCase(fieldName), errorMessage);
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Map<String, String>> handleNotFoundExceptions(BaseFormatException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrors());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("O valor '%s' é inválido para o parâmetro '%s'", ex.getValue(), ex.getName());
        Map<String, String> json = new HashMap<>();
        json.put("error", errorMessage);
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidDateFormatException.class, BaseFormatException.class})
    public ResponseEntity<Map<String, String>> handleInvalidDateFormat(BaseFormatException ex) {
        return ResponseEntity.badRequest().body(ex.getErrors());
    }

    private String camelToSnakeCase(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}