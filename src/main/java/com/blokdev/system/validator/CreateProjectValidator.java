package com.blokdev.system.validator;

import com.blokdev.system.dao.ProjectDao;
import com.blokdev.system.dto.CreateProjectDTO;
import com.blokdev.system.util.LocalDateFormatter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateProjectValidator implements Validator<CreateProjectDTO> {
    private static final CreateProjectValidator INSTANCE = new CreateProjectValidator();
    private final ProjectDao projectDao = ProjectDao.getInstance();

    @Override
    public ValidationResult isValid(CreateProjectDTO object) {
        ValidationResult validationResult = new ValidationResult();

        if (projectDao.findByName(object.getName()).isPresent()) {
            validationResult.addError(ValidationError.of("invalid.name", "Name must be unique"));
        }

        if (!LocalDateFormatter.isValid(object.getStartDate())) {
            validationResult.addError(ValidationError.of("invalid.date", "Date of incorrect format"));
        } else {
            var date = LocalDateFormatter.format(object.getStartDate());
            if (date.isBefore(LocalDate.now())) {
                validationResult.addError(ValidationError.of("invalid.date", "Date earlier than the current date"));
            }
        }

        return validationResult;
    }

    public static CreateProjectValidator getInstance() {
        return INSTANCE;
    }
}
