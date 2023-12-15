package com.blokdev.system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProjectDTO {
    String name;
    String description;
    String startDate;
}
