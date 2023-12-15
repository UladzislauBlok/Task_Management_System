package com.blokdev.system.service;

import com.blokdev.system.dao.ProjectDao;
import com.blokdev.system.dto.CreateProjectDTO;
import com.blokdev.system.entity.Project;
import com.blokdev.system.exception.ValidationException;
import com.blokdev.system.mapper.CreateProjectMapper;
import com.blokdev.system.validator.CreateProjectValidator;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProjectService {
    private static final ProjectService INSTANCE = new ProjectService();
    private final CreateProjectValidator createProjectValidator = CreateProjectValidator.getInstance();
    private final CreateProjectMapper createProjectMapper = CreateProjectMapper.getInstance();
    private final ProjectDao projectDao = ProjectDao.getInstance();

    public Project createProject(CreateProjectDTO createProjectDTO) {
        var validationResult = createProjectValidator.isValid(createProjectDTO);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getValidationErrorList());
        }
        var project = createProjectMapper.mapFrom(createProjectDTO);
        return projectDao.save(project);
    }

    public static ProjectService getInstance() {
        return INSTANCE;
    }
}
