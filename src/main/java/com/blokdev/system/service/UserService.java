package com.blokdev.system.service;

import com.blokdev.system.dao.UserDao;
import com.blokdev.system.dto.CreateUserDTO;
import com.blokdev.system.dto.UserDTO;
import com.blokdev.system.exception.EntryNotFoundException;
import com.blokdev.system.exception.ValidationException;
import com.blokdev.system.mapper.CreateUserMapper;
import com.blokdev.system.mapper.UserMapper;
import com.blokdev.system.validator.CreateUserValidator;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();
    private final ProjectService projectService = ProjectService.getInstance();

    @SneakyThrows
    public void create(CreateUserDTO createUserDTO) {
        var validationResult = createUserValidator.isValid(createUserDTO);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getValidationErrorList());
        }
        var user = createUserMapper.mapFrom(createUserDTO);
        var savedUser = userDao.save(user);
        imageService.upload(savedUser.getId(), createUserDTO.getImage().getSubmittedFileName(),
                createUserDTO.getImage().getInputStream()); // using userId like unique image index
    }

    public List<UserDTO> getAllUsers() {
        return userDao.findAll().stream()
                .map(userMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllUsersWithoutProject() {
        return getAllUsers().stream()
                .filter(userDTO -> userDTO.getProject() == null)
                .toList();
    }

    public UserDTO getUserById(Long userId) {
        var user = userDao.findById(userId)
                .orElseThrow(EntryNotFoundException::new);

        return userMapper.mapFrom(user);
    }

    public Optional<UserDTO> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(userMapper::mapFrom);
    }

    public void addUserToProject(Long userId, Long projectId) {
        var user = userDao.findById(userId)
                .orElseThrow(EntryNotFoundException::new);

        user.setProject(projectService.getProjectEntryById(projectId));
        userDao.update(user);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
