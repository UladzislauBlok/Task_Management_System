package com.blokdev.system.dao;

import com.blokdev.system.entity.Role;
import com.blokdev.system.entity.User;
import com.blokdev.system.exception.EntryNotFoundException;
import com.blokdev.system.util.ConnectionManager;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();
    private final ProjectDao projectDao = ProjectDao.getInstance();
    private static final String FIND_ALL_SQL = """
            SELECT id, first_name, last_name, password, email, role, image, project_id_fk
            FROM users;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, first_name, last_name, password, email, role, image, project_id_fk
            FROM users
            WHERE id = ?
            """;
    private static final String FIND_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT id, first_name, last_name, password, email, role, image, project_id_fk
            FROM users
            WHERE email = ? AND password = ?
            """;
    private static final String FIND_BY_EMAIL_SQL = """
            SELECT id, first_name, last_name, password, email, role, image, project_id_fk
            FROM users
            WHERE email = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String INSERT_SQL = """
            INSERT INTO users (first_name, last_name, password, email, role, image, project_id_fk)
            VALUES (?,?,?,?,?,?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE users
            SET first_name = ?,
            last_name = ?,
            password = ?,
            email = ?,
            role = ?,
            image = ?,
            project_id_fk = ?
            WHERE id = ?
            """;

    @Override
    @SneakyThrows
    public List<User> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<User> userList = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(buildUser(resultSet, connection));
            }
            return userList;
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUser(resultSet, connection));
            } else {
                return Optional.empty();
            }
        }
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD_SQL)) {
            statement.setString(1, email);
            statement.setString(2, password);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUser(resultSet, connection));
            } else {
                return Optional.empty();
            }
        }
    }

    @SneakyThrows
    public Optional<User> findByEmail(String email) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {
            statement.setString(1, email);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUser(resultSet, connection));
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    @SneakyThrows
    public User save(User entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(INSERT_SQL, RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getRole().name());
            statement.setString(6, entity.getImage());

            statement.executeUpdate();

            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong("id"));
            }
            return entity;
        }
    }

    @Override
    @SneakyThrows
    public boolean update(User entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getRole().name());
            statement.setString(6, entity.getImage());
            statement.setLong(7, entity.getId());
            return statement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    private User buildUser(ResultSet resultSet) {
        return User.builder()
                .id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .role(Role.valueOf(resultSet.getString("role")))
                .image(resultSet.getString("image"))
                .project(projectDao.findById(
                        resultSet.getLong("project_id_fk"))
                        .orElseThrow(EntryNotFoundException::new))
                .build();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
