package com.blokdev.system.validator;

import com.blokdev.system.dao.TaskDao;
import com.blokdev.system.dto.CreateTaskDTO;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateTaskValidator implements Validator<CreateTaskDTO>{
    private static final CreateTaskValidator INSTANCE = new CreateTaskValidator();

    private final TaskDao taskDao = TaskDao.getInstance();

    @Override
    public ValidationResult isValid(CreateTaskDTO object) {
        ValidationResult validationResult = new ValidationResult();

        if (taskDao.findByNameAndProjectId(object.getName(), object.getProjectId()).isPresent()) {
            validationResult.addError(ValidationError.of("duplicate.name.projectID", "The task name is repeated within the same project"));
        }

        return validationResult;
    }

    public static CreateTaskValidator getInstance() {
        return INSTANCE;
    }
}
