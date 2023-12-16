package com.blokdev.system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProjectDTO {
    private String name;
    private String description;
    private String startDate;
}
