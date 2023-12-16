package com.blokdev.system.service;

import com.blokdev.system.dao.ProjectDao;
import com.blokdev.system.dto.CreateProjectDTO;
import com.blokdev.system.dto.ProjectDTO;
import com.blokdev.system.exception.EntryNotFoundException;
import com.blokdev.system.exception.ValidationException;
import com.blokdev.system.mapper.CreateProjectMapper;
import com.blokdev.system.mapper.ProjectMapper;
import com.blokdev.system.validator.CreateProjectValidator;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProjectService {
    private static final ProjectService INSTANCE = new ProjectService();
    private final CreateProjectValidator createProjectValidator = CreateProjectValidator.getInstance();
    private final CreateProjectMapper createProjectMapper = CreateProjectMapper.getInstance();
    private final ProjectDao projectDao = ProjectDao.getInstance();
    private final ProjectMapper projectMapper = ProjectMapper.getInstance();

    public void createProject(CreateProjectDTO createProjectDTO) {
        var validationResult = createProjectValidator.isValid(createProjectDTO);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getValidationErrorList());
        }
        var project = createProjectMapper.mapFrom(createProjectDTO);
        projectDao.save(project);
    }

    public List<ProjectDTO> getAllProject() {
        return projectDao.findAll().stream()
                .map(projectMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public ProjectDTO getProjectById(Long id) {
        var project = projectDao.findById(id)
                .orElseThrow(EntryNotFoundException::new);
        return projectMapper.mapFrom(project);
    }

    public static ProjectService getInstance() {
        return INSTANCE;
    }
}
