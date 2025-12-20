package com.example.mytaskmanager.utils.validation.rules;

public class MinLengthRule implements IValidationRule {
    private final int min;
    private final String message;

    public MinLengthRule(int min, String message) {
        this.min = min;
        this.message = message;
    }

    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= min;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }
}
