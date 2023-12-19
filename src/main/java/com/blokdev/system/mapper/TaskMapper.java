package com.blokdev.system.mapper;

import com.blokdev.system.dto.TaskDTO;
import com.blokdev.system.entity.Task;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TaskMapper implements Mapper<Task, TaskDTO> {
    private static final TaskMapper INSTANCE = new TaskMapper();

    private final TaskEventMapper taskEventMapper = TaskEventMapper.getInstance();

    @Override
    public TaskDTO mapFrom(Task object) {
        return TaskDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .description(object.getDescription())
                .status(object.getStatus())
                .projectId(object.getProjectId())
                .taskEventList(object.getTaskEventList().stream()
                        .map(taskEventMapper::mapFrom)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static TaskMapper getInstance() {
        return INSTANCE;
    }
}
