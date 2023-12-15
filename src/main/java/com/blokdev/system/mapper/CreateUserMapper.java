package com.blokdev.system.mapper;

import com.blokdev.system.dto.CreateUserDTO;
import com.blokdev.system.entity.Role;
import com.blokdev.system.entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDTO, User>{
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(CreateUserDTO object) {
        return User.builder()
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(Role.valueOf(object.getRole()))
                .image(object.getImage().getSubmittedFileName())
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
