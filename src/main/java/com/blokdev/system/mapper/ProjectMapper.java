package com.blokdev.system.mapper;

import com.blokdev.system.dto.ProjectDTO;
import com.blokdev.system.entity.Project;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProjectMapper implements Mapper<Project, ProjectDTO> {
    private static final ProjectMapper INSTANCE = new ProjectMapper();

    @Override
    public ProjectDTO mapFrom(Project object) {
        return ProjectDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .description(object.getDescription())
                .startDate(object.getStartDate())
                .build();
    }

    public static ProjectMapper getInstance() {
        return INSTANCE;
    }
}
