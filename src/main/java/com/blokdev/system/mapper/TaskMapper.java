package com.blokdev.system.mapper;

import com.blokdev.system.dto.TaskDTO;
import com.blokdev.system.entity.Task;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TaskMapper implements Mapper<Task, TaskDTO> {
    private static final TaskMapper INSTANCE = new TaskMapper();

    @Override
    public TaskDTO mapFrom(Task object) {
        return TaskDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .description(object.getDescription())
                .status(object.getStatus())
                .taskEventList(object.getTaskEventList())
                .build();
    }

    public static TaskMapper getInstance() {
        return INSTANCE;
    }
}
