package com.blokdev.system.dto;

import jakarta.servlet.http.Part;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private Part image;
}
