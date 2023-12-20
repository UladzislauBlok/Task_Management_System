package com.blokdev.system.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateTaskEventDTO {
    Long taskId;
    String description;
    String eventUserName;
}
