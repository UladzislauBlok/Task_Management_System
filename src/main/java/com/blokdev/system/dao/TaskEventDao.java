package com.blokdev.system.dao;

import com.blokdev.system.entity.TaskEvent;
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
public class TaskEventDao implements Dao<Long, TaskEvent>{
    private static final TaskEventDao INSTANCE = new TaskEventDao();

    private static final String FIND_ALL_SQL = """
            SELECT id, task_id_fk, description, event_user_name
            FROM task_events
            """;
    private static final String FIND_ALL_BY_TASK_ID_SQL = """
            SELECT id, task_id_fk, description, event_user_name
            FROM task_events
            WHERE task_id_fk = ?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, task_id_fk, description, event_user_name
            FROM task_events
            WHERE id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM task_events
            WHERE id = ?
            """;
    private static final String INSERT_SQL = """
            INSERT INTO task_events(task_id_fk, description, event_user_name)
            VALUES (?,?,?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE task_events
            SET description = ?,
            event_user_name = ?
            WHERE id = ?
            """;

    @Override
    @SneakyThrows
    public List<TaskEvent> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<TaskEvent> taskEventList = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                taskEventList.add(buildTaskEvent(resultSet));
            }
            return taskEventList;
        }
    }

    @SneakyThrows
    public List<TaskEvent> findAllByTskId(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_BY_TASK_ID_SQL)) {
            List<TaskEvent> taskEventList = new ArrayList<>();
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                taskEventList.add(buildTaskEvent(resultSet));
            }
            return taskEventList;
        }
    }

    @Override
    @SneakyThrows
    public Optional<TaskEvent> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(buildTaskEvent(resultSet));
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
    public TaskEvent save(TaskEvent entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(INSERT_SQL, RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getTaskId());
            statement.setString(2, entity.getDescription());
            statement.setString(3, entity.getEventUserName());

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
    public boolean update(TaskEvent entity) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, entity.getDescription());
            statement.setString(2, entity.getEventUserName());
            statement.setLong(3, entity.getId());

            return statement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    private TaskEvent buildTaskEvent(ResultSet resultSet) {
        return TaskEvent.builder()
                .id(resultSet.getLong("id"))
                .taskId(resultSet.getLong("task_id_fk"))
                .description(resultSet.getString("description"))
                .eventUserName(resultSet.getString("event_user_name"))
                .build();
    }

    public static TaskEventDao getInstance() {
        return INSTANCE;
    }
}
