package com.example.mytaskmanager.utils.validation.rules;

public interface IValidationRule {
    boolean isValid(String value);
    String getErrorMessage();
}
