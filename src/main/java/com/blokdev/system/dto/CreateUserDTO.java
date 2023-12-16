package com.blokdev.system.dto;

import jakarta.servlet.http.Part;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDTO {
    String firstName;
    String lastName;
    String password;
    String email;
    String role;
    Part image;
}
