package com.blokdev.system.exception;

import com.blokdev.system.validator.ValidationError;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private final List<ValidationError> validationErrorList;

    public ValidationException(List<ValidationError> validationErrorList) {
        this.validationErrorList = validationErrorList;
    }
}
