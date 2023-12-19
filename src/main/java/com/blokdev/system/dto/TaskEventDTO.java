package com.blokdev.system.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskEventDTO {
    Long id;
    Long taskId;
    String description;
    String eventUserName;
}
