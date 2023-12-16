package com.blokdev.system.dao;

import com.blokdev.system.entity.Status;
import com.blokdev.system.entity.Task;
import com.blokdev.system.util.ConnectionManager;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TaskDao implements Dao<Long, Task> {
    private static final TaskDao INSTANCE = new TaskDao();

    private static final String FIND_ALL_SQL = """
            SELECT id, name, description, status, project_id_fk
            FROM tasks
            """;
    private static final String FIND_ALL_BY_PROJECT_ID_SQL = """
            SELECT id, name, description, status, project_id_fk
            FROM tasks
            WHERE project_id_fk = ?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, description, status, project_id_fk
            FROM tasks
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM tasks
            WHERE id = ?
            """;
    private static final String INSERT_SQL = """
            INSERT INTO tasks(name, description, status, project_id_fk)
            VALUES (?,?,?,?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE tasks
            SET name = ?,
            description = ?,
            status = ?
            WHERE id = ?
            """;

    private final TaskEventDao taskEventDao = TaskEventDao.getInstance();

    @Override
    @SneakyThrows
    public List<Task> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Task> taskList = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                taskList.add(buildTask(resultSet, connection));
            }
            return taskList;
        }
    }

    @SneakyThrows
    public List<Task> findAllByProjectId(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_BY_PROJECT_ID_SQL)) {
            List<Task> taskList = new ArrayList<>();
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                taskList.add(buildTask(resultSet, connection));
            }
            return taskList;
        }
    }

    @Override
    @SneakyThrows
    public Optional<Task> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildTask(resultSet, connection));
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
    public Task save(Task entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(INSERT_SQL, RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setString(3, entity.getStatus().name());
            statement.setLong(4, entity.getProjectId());

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
    public boolean update(Task entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getDescription());
                statement.setString(3, entity.getStatus().name());
                statement.setLong(4, entity.getId());

                return statement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    private Task buildTask(ResultSet resultSet, Connection connection) {
        return Task.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .status(Status.valueOf(resultSet.getString("status")))
                .taskEventList(taskEventDao.findAllByTskId(
                        resultSet.getLong("project_id_fk"),
                        connection))
                .build();
    }

    public static TaskDao getInstance() {
        return INSTANCE;
    }
}
