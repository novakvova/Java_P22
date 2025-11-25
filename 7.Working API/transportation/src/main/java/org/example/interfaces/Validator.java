package org.example.interfaces;

import org.example.data.dto.validation.FieldError;

import java.util.List;

public interface Validator<T> {
    List<FieldError> validate(T object);
}