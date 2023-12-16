package com.blokdev.system.service;

import com.blokdev.system.dao.TaskDao;
import com.blokdev.system.dto.TaskDTO;
import com.blokdev.system.mapper.TaskMapper;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TaskService {
    private static final TaskService INSTANCE = new TaskService();
    private final TaskDao taskDao = TaskDao.getInstance();
    private final TaskMapper taskMapper = TaskMapper.getInstance();

    public List<TaskDTO> getTaskListByProjectId(Long projectId) {
        return taskDao.findAllByProjectId(projectId).stream()
                .map(taskMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public static TaskService getInstance() {
        return INSTANCE;
    }
}
