package com.blokdev.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private Long projectId;
    private List<TaskEvent> taskEventList;
}
