package com.blokdev.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEvent {
    private Long id;
    private Long taskId;
    private String description;
    private String eventUserName;
}
