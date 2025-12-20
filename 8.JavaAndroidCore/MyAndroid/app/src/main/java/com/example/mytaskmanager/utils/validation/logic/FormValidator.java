package com.example.mytaskmanager.utils.validation.logic;

import java.util.ArrayList;
import java.util.List;

public class FormValidator {

    private final List<FieldValidator> fields = new ArrayList<>();

    public FormValidator addField(FieldValidator field) {
        fields.add(field);
        return this;
    }

    public boolean validate() {
        boolean isValid = true;

        for (FieldValidator field : fields) {
            if (!field.validate()) {
                isValid = false;
            }
        }

        return isValid;
    }
}