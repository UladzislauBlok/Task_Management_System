package com.blokdev.system.mapper;

import com.blokdev.system.dto.CreateTaskDTO;
import com.blokdev.system.entity.Status;
import com.blokdev.system.entity.Task;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateTaskMapper implements Mapper<CreateTaskDTO, Task> {
    private final static CreateTaskMapper INSTANCE = new CreateTaskMapper();
    @Override
    public Task mapFrom(CreateTaskDTO object) {
        return Task.builder()
                .name(object.getName())
                .description(object.getDescription())
                .status(Status.NEW)
                .projectId(object.getProjectId())
                .build();
    }

    public static CreateTaskMapper getInstance() {
        return INSTANCE;
    }
}
