package com.blokdev.system.mapper;

import com.blokdev.system.dto.CreateTaskEventDTO;
import com.blokdev.system.entity.TaskEvent;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateTaskEventMapper implements Mapper<CreateTaskEventDTO, TaskEvent> {
    private static final CreateTaskEventMapper INSTANCE = new CreateTaskEventMapper();

    @Override
    public TaskEvent mapFrom(CreateTaskEventDTO object) {
        return TaskEvent.builder()
                .taskId(object.getTaskId())
                .description(object.getDescription())
                .eventUserName(object.getEventUserName())
                .build();
    }

    public static CreateTaskEventMapper getInstance() {
        return INSTANCE;
    }
}
