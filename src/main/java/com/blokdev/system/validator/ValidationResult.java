package com.blokdev.system.validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationResult {
    private final List<ValidationError> validationErrorList = new ArrayList<>();

    public void addError(ValidationError validationError) {
        validationErrorList.add(validationError);
    }

    public boolean isValid() {
        return validationErrorList.isEmpty();
    }
}
