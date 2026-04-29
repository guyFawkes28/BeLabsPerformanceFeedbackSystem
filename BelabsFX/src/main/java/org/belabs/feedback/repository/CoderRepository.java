package org.belabs.feedback.repository;

import org.belabs.feedback.model.Coder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoderRepository {
    private final DatabaseConnection databaseConnection;

    public CoderRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Coder findByEmail(String email) {
        String sql = "SELECT id, nombre, email FROM coders WHERE email = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Coder(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("email"));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to find coder by email", exception);
        }
        return null;
    }

    public List<Coder> findAll() {
        List<Coder> coders = new ArrayList<>();
        String sql = "SELECT id, nombre, email FROM coders ORDER BY nombre";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                coders.add(new Coder(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("email")));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to load coders", exception);
        }
        return coders;
    }
}
