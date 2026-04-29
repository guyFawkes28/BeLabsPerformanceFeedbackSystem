package org.belabs.feedback.repository;

import org.belabs.feedback.model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillRepository {
    private final DatabaseConnection databaseConnection;

    public SkillRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<Skill> findAll() {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT id, nombre, alto, medio, bajo FROM skills ORDER BY nombre";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                skills.add(mapSkill(resultSet));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to load skills", exception);
        }
        return skills;
    }

    public void save(Skill skill) {
        String sql = "INSERT INTO skills (nombre, alto, medio, bajo) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, skill.getNombre());
            statement.setString(2, skill.getAlto());
            statement.setString(3, skill.getMedio());
            statement.setString(4, skill.getBajo());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to save skill", exception);
        }
    }

    public void update(Skill skill) {
        String sql = "UPDATE skills SET nombre = ?, alto = ?, medio = ?, bajo = ? WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, skill.getNombre());
            statement.setString(2, skill.getAlto());
            statement.setString(3, skill.getMedio());
            statement.setString(4, skill.getBajo());
            statement.setInt(5, skill.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to update skill", exception);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM skills WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to delete skill", exception);
        }
    }

    public Skill findById(int id) {
        String sql = "SELECT id, nombre, alto, medio, bajo FROM skills WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapSkill(resultSet);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to load skill", exception);
        }
        return null;
    }

    private Skill mapSkill(ResultSet resultSet) throws SQLException {
        return new Skill(
                resultSet.getInt("id"),
                resultSet.getString("nombre"),
                resultSet.getString("alto"),
                resultSet.getString("medio"),
                resultSet.getString("bajo")
        );
    }
}
