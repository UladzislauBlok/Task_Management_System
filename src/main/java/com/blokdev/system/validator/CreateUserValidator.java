package com.blokdev.system.validator;

import com.blokdev.system.dao.UserDao;
import com.blokdev.system.dto.CreateUserDTO;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDTO> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    private final UserDao userDao = UserDao.getInstance();

    @Override
    public ValidationResult isValid(CreateUserDTO object) {
        ValidationResult validationResult = new ValidationResult();

        if (userDao.findByEmail(object.getEmail()).isPresent()) {
            validationResult.addError(ValidationError.of("duplicate.email", "Email has been used"));
        }

        if (object.getPassword().length() < 8) {
            validationResult.addError(ValidationError.of("invalid.password", "Password is too short"));
        }

        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
