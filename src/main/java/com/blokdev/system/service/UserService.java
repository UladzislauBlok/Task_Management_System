package com.blokdev.system.service;

import com.blokdev.system.dao.UserDao;
import com.blokdev.system.dto.CreateUserDTO;
import com.blokdev.system.entity.User;
import com.blokdev.system.exception.ValidationException;
import com.blokdev.system.mapper.CreateUserMapper;
import com.blokdev.system.validator.CreateUserValidator;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final String IMAGE_FOLDER = "users";
    private static final String UNIQUE_IMAGE_NAME_PATTERN = "%s/%d_%s";
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    @SneakyThrows
    public User create(CreateUserDTO createUserDTO) {
        var validationResult = createUserValidator.isValid(createUserDTO);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getValidationErrorList());
        }
        var user = createUserMapper.mapFrom(createUserDTO);
        var savedUser = userDao.save(user);
        imageService.upload(UNIQUE_IMAGE_NAME_PATTERN.formatted(IMAGE_FOLDER, savedUser.getId(), createUserDTO.getImage().getSubmittedFileName()),
                createUserDTO.getImage().getInputStream()); // using userId like unique image index
        return savedUser;
    }
    public static UserService getInstance() {
        return INSTANCE;
    }
}
