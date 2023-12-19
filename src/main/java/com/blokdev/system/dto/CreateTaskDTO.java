package com.blokdev.system.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateTaskDTO {
    String name;
    String description;
    Long projectId;
}
