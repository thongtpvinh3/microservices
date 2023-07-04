package com.thong.connectionpooldemo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConnectionPool {
    private String url;
    private String username;
    private String password;
    private int initialSize;
    private int maxPoolSize;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();

    public ConnectionPool(String url, String username, String password, int initialSize, int maxPoolSize) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.initialSize = initialSize;
        this.maxPoolSize = maxPoolSize;

        connectionPool = createConnectionPool();
    }

    private List<Connection> createConnectionPool() {
        List<Connection> pool = new ArrayList<>();
        try {
            for (int i = 0; i < initialSize; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                pool.add(connection);
            }
        } catch (SQLException e) {
            System.err.println("Error creating connection pool: " + e.getMessage());
        }
        return pool;
    }

    public synchronized Connection getConnection() {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < maxPoolSize) {
                try {
                    Connection connection = DriverManager.getConnection(url, username, password);
                    usedConnections.add(connection);
                    return connection;
                } catch (SQLException e) {
                    System.err.println("Error creating new connection: " + e.getMessage());
                    return null;
                }
            } else {
                System.err.println("All connections are busy!");
                return null;
            }
        } else {
            Connection connection = connectionPool.remove(connectionPool.size() - 1);
            usedConnections.add(connection);
            return connection;
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        connectionPool.add(connection);
    }

    public synchronized void closeAllConnections() {
        for (Connection connection : connectionPool) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        connectionPool.clear();
        usedConnections.clear();
    }
}
