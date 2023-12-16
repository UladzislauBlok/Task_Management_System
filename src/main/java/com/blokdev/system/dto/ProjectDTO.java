package com.blokdev.system.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ProjectDTO {
    Long id;
    String name;
    String description;
    LocalDate startDate;
}
