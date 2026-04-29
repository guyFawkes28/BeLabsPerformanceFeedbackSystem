package org.belabs.feedback.repository;

import org.belabs.feedback.model.Laboratory;
import org.belabs.feedback.model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LaboratoryRepository {
    private final DatabaseConnection databaseConnection;

    public LaboratoryRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void save(Laboratory laboratory) {
        String insertLab = "INSERT INTO laboratories (nombre) VALUES (?)";
        String insertSkillLink = "INSERT INTO laboratory_skills (laboratory_id, skill_id) VALUES (?, ?)";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement labStatement = connection.prepareStatement(insertLab, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            labStatement.setString(1, laboratory.getNombre());
            labStatement.executeUpdate();

            int laboratoryId;
            try (ResultSet keys = labStatement.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("Laboratory ID was not generated");
                }
                laboratoryId = keys.getInt(1);
            }

            try (PreparedStatement linkStatement = connection.prepareStatement(insertSkillLink)) {
                for (Skill skill : laboratory.getSkills()) {
                    linkStatement.setInt(1, laboratoryId);
                    linkStatement.setInt(2, skill.getId());
                    linkStatement.addBatch();
                }
                linkStatement.executeBatch();
            }
            connection.commit();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to save laboratory", exception);
        }
    }

    public List<Laboratory> findAll() {
        List<Laboratory> laboratories = new ArrayList<>();
        String sql = "SELECT id, nombre FROM laboratories ORDER BY nombre";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Laboratory laboratory = new Laboratory(resultSet.getInt("id"), resultSet.getString("nombre"));
                laboratory.setSkills(findSkillsByLaboratoryId(laboratory.getId()));
                laboratories.add(laboratory);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to load laboratories", exception);
        }
        return laboratories;
    }

    public List<Skill> findSkillsByLaboratoryId(int id) {
        List<Skill> skills = new ArrayList<>();
        String sql = """
                SELECT s.id, s.nombre, s.alto, s.medio, s.bajo
                FROM skills s
                INNER JOIN laboratory_skills ls ON ls.skill_id = s.id
                WHERE ls.laboratory_id = ?
                ORDER BY s.nombre
                """;
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    skills.add(new Skill(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("alto"),
                            resultSet.getString("medio"),
                            resultSet.getString("bajo")
                    ));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to load laboratory skills", exception);
        }
        return skills;
    }
}
