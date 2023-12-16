package com.blokdev.system.dao;

import com.blokdev.system.entity.Project;
import com.blokdev.system.util.ConnectionManager;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProjectDao implements Dao<Long, Project> {
    private static final ProjectDao INSTANCE = new ProjectDao();


    private static final String FIND_ALL_SQL = """
            SELECT id, name, description, start_date
            FROM projects;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, description, start_date
            FROM projects
            WHERE id = ?
            """;
    private static final String FIND_BY_NAME_SQL = """
            SELECT id, name, description, start_date
            FROM projects
            WHERE name = ?
            """;
    private static final String DELETE_SQL = """
            DELETE
            FROM projects
            WHERE id = ?
            """;
    private static final String INSERT_SQL = """
            INSERT INTO projects(name, description, start_date)
            VALUES (?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE projects
            SET name = ?,
            description = ?
            WHERE id = ?
            """;


    @Override
    @SneakyThrows
    public List<Project> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Project> projectList = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                projectList.add(buildProject(resultSet));
            }
            return projectList;
        }
    }

    @Override
    @SneakyThrows
    public Optional<Project> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildProject(resultSet));
            } else {
                return Optional.empty();
            }
        }
    }

    @SneakyThrows
    public Optional<Project> findByName(String name) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, name);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildProject(resultSet));
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
    public Project save(Project entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(INSERT_SQL, RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setDate(3, Date.valueOf(entity.getStartDate()));

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
    public boolean update(Project entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getId());

            return statement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    private Project buildProject(ResultSet resultSet) {
        return Project.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .startDate(resultSet.getDate("start_date").toLocalDate())
                .build();
    }

    public static ProjectDao getInstance() {
        return INSTANCE;
    }
}
