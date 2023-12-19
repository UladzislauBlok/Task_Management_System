package com.blokdev.system.service;

import com.blokdev.system.dao.TaskDao;
import com.blokdev.system.dto.CreateTaskDTO;
import com.blokdev.system.dto.TaskDTO;
import com.blokdev.system.exception.EntryNotFoundException;
import com.blokdev.system.exception.ValidationException;
import com.blokdev.system.mapper.CreateTaskMapper;
import com.blokdev.system.mapper.TaskMapper;
import com.blokdev.system.validator.CreateTaskValidator;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TaskService {
    private static final TaskService INSTANCE = new TaskService();
    private final TaskDao taskDao = TaskDao.getInstance();
    private final TaskMapper taskMapper = TaskMapper.getInstance();
    private final CreateTaskMapper createTaskMapper = CreateTaskMapper.getInstance();
    private final CreateTaskValidator createTaskValidator = CreateTaskValidator.getInstance();

    public List<TaskDTO> getTaskListByProjectId(Long projectId) {
        return taskDao.findAllByProjectId(projectId).stream()
                .map(taskMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public void create(CreateTaskDTO createTaskDTO) {
        var validationResult = createTaskValidator.isValid(createTaskDTO);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getValidationErrorList());
        }
        var task = createTaskMapper.mapFrom(createTaskDTO);
        taskDao.save(task);
    }

    public TaskDTO getTaskById(Long id) {
        var task = taskDao.findById(id)
                .orElseThrow(EntryNotFoundException::new);
        return taskMapper.mapFrom(task);
    }

    public static TaskService getInstance() {
        return INSTANCE;
    }
}
