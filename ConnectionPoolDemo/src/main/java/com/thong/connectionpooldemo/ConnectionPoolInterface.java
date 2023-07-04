package com.thong.connectionpooldemo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ConnectionPoolInterface {

    Connection getConnection() throws SQLException;

    boolean releaseConnection(Connection connection);

    List<Connection> getConnectionPool();

    int getSize();

    String getUrl();

    String getUser();

    String getPassword();

    void shutdown() throws SQLException;
}
