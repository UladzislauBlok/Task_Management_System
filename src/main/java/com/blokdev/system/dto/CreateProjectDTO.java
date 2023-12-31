package com.blokdev.system.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateProjectDTO {
    String name;
    String description;
    String startDate;
}
