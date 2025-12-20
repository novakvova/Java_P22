package com.example.mytaskmanager.utils.validation.rules;

public class RequiredRule implements IValidationRule {
    private final String message;

    public RequiredRule(String message) {
        this.message = message;
    }

    @Override
    public boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return message;
    }
}
