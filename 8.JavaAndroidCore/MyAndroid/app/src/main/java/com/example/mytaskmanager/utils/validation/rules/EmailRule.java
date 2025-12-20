package com.example.mytaskmanager.utils.validation.rules;

public class EmailRule implements IValidationRule {
    private final String message;

    public EmailRule(String message) {
        this.message = message;
    }

    @Override
    public boolean isValid(String value) {
        return value != null &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    @Override
    public String getErrorMessage() {
        return message;
    }
}
