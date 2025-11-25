package org.example.data.common;

import org.example.data.dto.validation.FieldError;

import java.util.List;

public class ValidationException extends RuntimeException {
    private final List<FieldError> errors;

    public ValidationException(List<FieldError> errors) {
        this.errors = errors;
    }

    public List<FieldError> getErrors() {
        return errors;
    }
}