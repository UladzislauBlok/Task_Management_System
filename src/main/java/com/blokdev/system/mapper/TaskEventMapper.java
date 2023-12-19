package com.blokdev.system.mapper;

import com.blokdev.system.dto.TaskEventDTO;
import com.blokdev.system.entity.TaskEvent;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TaskEventMapper implements Mapper<TaskEvent, TaskEventDTO> {
    private static final TaskEventMapper INSTANCE = new TaskEventMapper();

    @Override
    public TaskEventDTO mapFrom(TaskEvent object) {
        return TaskEventDTO.builder()
                .id(object.getId())
                .taskId(object.getTaskId())
                .description(object.getDescription())
                .eventUserName(object.getEventUserName())
                .build();
    }

    public static TaskEventMapper getInstance() {
        return INSTANCE;
    }
}
