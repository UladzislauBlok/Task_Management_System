package com.blokdev.hotelsystem.dao;

import com.blokdev.hotelsystem.entity.Role;
import com.blokdev.hotelsystem.entity.User;
import com.blokdev.hotelsystem.util.ConnectionManager;
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

    private static final String FIND_ALL_SQL = """
            SELECT id, first_name, last_name, password, email, role, image
            FROM users;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, first_name, last_name, password, email, role, image
            FROM users
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String INSERT_SQL = """
            INSERT INTO users (first_name, last_name, password, email, role, image)
            VALUES (?,?,?,?,?,?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE users
            SET first_name = ?,
            last_name = ?,
            password = ?,
            email = ?,
            role = ?,
            image = ?
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
                userList.add(buildUser(resultSet));
            }
            return userList;
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUser(resultSet));
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
                .build();
    }
}
