package org.belabs.feedback.repository;

import org.belabs.feedback.model.AdminUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminUserRepository {
    private final DatabaseConnection databaseConnection;

    public AdminUserRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public AdminUser findByEmail(String email) {
        String sql = "SELECT id, nombre, email, password_hash FROM admin_users WHERE email = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new AdminUser(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("email"),
                            resultSet.getString("password_hash")
                    );
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to find admin user", exception);
        }
        return null;
    }
}
