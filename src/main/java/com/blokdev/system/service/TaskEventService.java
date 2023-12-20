package com.blokdev.system.service;

import com.blokdev.system.dao.TaskEventDao;
import com.blokdev.system.dto.CreateTaskEventDTO;
import com.blokdev.system.mapper.CreateTaskEventMapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TaskEventService {
    private static final TaskEventService INSTANCE = new TaskEventService();
    private final TaskEventDao taskEventDao = TaskEventDao.getInstance();
    private final CreateTaskEventMapper createTaskEventMapper = CreateTaskEventMapper.getInstance();

    public void create(CreateTaskEventDTO createTaskEventDTO) {
        var taskEvent = createTaskEventMapper.mapFrom(createTaskEventDTO);
        taskEventDao.save(taskEvent);
    }

    public static TaskEventService getInstance() {
        return INSTANCE;
    }
}
