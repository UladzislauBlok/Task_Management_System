package com.blokdev.system.mapper;

import com.blokdev.system.dto.CreateProjectDTO;
import com.blokdev.system.entity.Project;
import com.blokdev.system.util.LocalDateFormatter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateProjectMapper implements Mapper<CreateProjectDTO, Project>{
    private static final CreateProjectMapper INSTANCE = new CreateProjectMapper();

    @Override
    public Project mapFrom(CreateProjectDTO object) {
        return Project.builder()
                .name(object.getName())
                .description(object.getDescription())
                .startDate(LocalDateFormatter.format(object.getStartDate()))
                .build();
    }

    public static CreateProjectMapper getInstance() {
        return INSTANCE;
    }
}
