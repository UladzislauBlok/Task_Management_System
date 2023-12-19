package com.blokdev.system.dto;

import com.blokdev.system.entity.Status;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class TaskDTO {
    Long id;
    String name;
    String description;
    Status status;
    Long projectId;
    List<TaskEventDTO> taskEventList;
}
