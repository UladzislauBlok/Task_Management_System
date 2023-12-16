package com.blokdev.system.mapper;

import com.blokdev.system.dto.UserDTO;
import com.blokdev.system.entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<User, UserDTO> {
    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDTO mapFrom(User object) {
        return UserDTO.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .role(object.getRole())
                .image(object.getImage())
                .project(object.getProject())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
