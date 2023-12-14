package com.blokdev.hotelsystem.util;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final int DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnections;

    static {
        loadDriver();
        initConnectionPool();
        registrationShutdownHook();
    }

    @SneakyThrows
    public static Connection get() {
        return pool.take();
    }

    @SneakyThrows
    private static Connection open() {
        return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
    }

    @SneakyThrows
    private static void loadDriver() {
        Class.forName(PropertiesUtil.get(DRIVER_KEY));
    }

    private static void initConnectionPool() {
        String sizePropFile = PropertiesUtil.get(POOL_SIZE_KEY);
        int size = sizePropFile == null ? DEFAULT_POOL_SIZE : Integer.parseInt(sizePropFile);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            var connection = open();
            var proxyInstance = (Connection)
                    Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close")
                            ? pool.add(connection)
                            : method.invoke(connection, args));
            pool.add(proxyInstance);
            sourceConnections.add(connection);
        }
    }

    @SneakyThrows
    public static void closePool() {
        for (Connection connection : sourceConnections) {
            connection.close();
        }
    }

    private static void registrationShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(ConnectionManager::closePool));
    }
}
