package cafe.users;

import cafe.database.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class JdbcUserRepository implements UserRepository {
    private final ConnectionPool connectionPool;

    public JdbcUserRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public User save(User user) {
        // get a connection from the connection pool
        // use prepared statement to insert the user into the database
        // return the user
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // use the connection to save the user
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Failed to save user, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.withId(generatedKeys.getLong(1));
                } else {
                    throw new RuntimeException("Failed to save user, no ID obtained.");
                }
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User findById(Long id) {
        return null;
    }
}
