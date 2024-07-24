package cafe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class JdbcConnectionPool implements ConnectionPool {
    private final String jdbcUrl;
    private final String user;
    private final String password;
    private final int maxPoolSize;
    private final Queue<PooledConnection> availableConnections = new LinkedList<>();

    public JdbcConnectionPool(String jdbcUrl, String user, String password, int maxPoolSize) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
        initializeConnections();
    }

    private void initializeConnections() {
        for (int i = 0; i < maxPoolSize; i++) {
            try {
                Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
                availableConnections.add(new PooledConnection(conn, this));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized Connection getConnection() throws SQLException {
        while (availableConnections.isEmpty()) {
            try {
                wait(); // Wait until a connection becomes available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new SQLException("Interrupted while waiting for a connec" +
                        "tion", e);
            }
        }
        PooledConnection connection = availableConnections.poll();
        // If the connection is closed, create a new one
        if (connection.getOriginalConnection().isClosed()) {
            return new PooledConnection(DriverManager.getConnection(jdbcUrl, user, password), this);
        }
        connection.isClosed = false; // Reset the closed status when reusing
        return connection;
    }

    @Override
    public synchronized void releaseConnection(PooledConnection connection) {
        if (connection != null) {
            availableConnections.add(connection);
            notifyAll(); // Notify waiting threads
        }
    }
}
