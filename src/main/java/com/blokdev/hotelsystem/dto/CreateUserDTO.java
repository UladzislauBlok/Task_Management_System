package com.blokdev.hotelsystem.dto;

import jakarta.servlet.http.Part;

public class CreateUserDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private Part image;
}
