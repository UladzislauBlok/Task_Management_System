package com.blokdev.system.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
