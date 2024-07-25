package cafe.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection() throws SQLException;

    void releaseConnection(PooledConnection connection);
}
