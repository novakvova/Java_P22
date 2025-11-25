interface ServerErrors {
    [key: string]: string[];
}

interface ValidationResult {
    fieldErrors: Record<string, string>;
    isValid: boolean;
}

export const parseServerValidationErrors = (errors: ServerErrors): ValidationResult => {
    const fieldErrors: Record<string, string> = {};

    Object.entries(errors).forEach(([key, messages]) => {
        if (messages.length > 0) {
            // Беремо перше повідомлення для конкретного поля
            fieldErrors[key] = messages[0];
        }
    });

    return {
        fieldErrors,
        isValid: Object.keys(fieldErrors).length === 0,
    };
};
