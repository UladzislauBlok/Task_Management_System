package com.blokdev.system.dto;

import com.blokdev.system.entity.Project;
import com.blokdev.system.entity.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {
    Long id;
    String firstName;
    String lastName;
    String email;
    Role role;
    String image;
    Project project;
}
