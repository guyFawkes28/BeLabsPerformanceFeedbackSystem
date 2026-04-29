package org.belabs.feedback.repository;

import org.belabs.feedback.util.PasswordUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseConnection {
    private final Properties properties = new Properties();

    public DatabaseConnection() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("database.properties not found");
            }
            properties.load(inputStream);
            Class.forName(properties.getProperty("db.driver"));
        } catch (IOException | ClassNotFoundException exception) {
            throw new IllegalStateException("Unable to load database configuration", exception);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password")
        );
    }

    public void initializeDatabase() throws SQLException {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS admin_users (
                        id SERIAL PRIMARY KEY,
                        nombre VARCHAR(100),
                        email VARCHAR(100) UNIQUE,
                        password_hash TEXT
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS coders (
                        id SERIAL PRIMARY KEY,
                        nombre VARCHAR(100),
                        email VARCHAR(100) UNIQUE
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS skills (
                        id SERIAL PRIMARY KEY,
                        nombre VARCHAR(100) UNIQUE,
                        alto TEXT,
                        medio TEXT,
                        bajo TEXT
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS laboratories (
                        id SERIAL PRIMARY KEY,
                        nombre VARCHAR(100)
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS laboratory_skills (
                        id SERIAL PRIMARY KEY,
                        laboratory_id INT REFERENCES laboratories(id) ON DELETE CASCADE,
                        skill_id INT REFERENCES skills(id)
                    )
                    """);
            cleanupDuplicates(connection);
            ensureUniqueConstraint(connection, "admin_users", "email", "admin_users_email_unique");
            ensureUniqueConstraint(connection, "coders", "email", "coders_email_unique");
            ensureUniqueConstraint(connection, "skills", "nombre", "skills_nombre_unique");
        }
        seedAdminUser();
        seedCoders();
        seedSkills();
    }

    private void seedAdminUser() throws SQLException {
        try (Connection connection = getConnection()) {
            if (!existsByColumn(connection, "admin_users", "email", "admin@belabs.com")) {
                try (PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO admin_users (nombre, email, password_hash) VALUES (?, ?, ?)")) {
                    insertStatement.setString(1, "Belabs Admin");
                    insertStatement.setString(2, "admin@belabs.com");
                    insertStatement.setString(3, PasswordUtil.hashPassword("admin123"));
                    insertStatement.executeUpdate();
                }
            }
        }
    }

    private void seedCoders() throws SQLException {
        List<String[]> coders = List.of(
                new String[]{"Ana Torres", "ana@belabs.com"},
                new String[]{"Luis Perez", "luis@belabs.com"},
                new String[]{"Marta Diaz", "marta@belabs.com"}
        );
        try (Connection connection = getConnection()) {
            for (String[] coder : coders) {
                if (!existsByColumn(connection, "coders", "email", coder[1])) {
                    try (PreparedStatement statement = connection.prepareStatement(
                            "INSERT INTO coders (nombre, email) VALUES (?, ?)")) {
                        statement.setString(1, coder[0]);
                        statement.setString(2, coder[1]);
                        statement.executeUpdate();
                    }
                }
            }
        }
    }

    private void seedSkills() throws SQLException {
        List<String[]> skills = new ArrayList<>();
        skills.add(new String[]{"Liderazgo", "Demuestra una solida capacidad para guiar y motivar a otros, promoviendo un entorno orientado a resultados.", "Evidencia habilidades de liderazgo en desarrollo, aunque puede fortalecer su capacidad para influir en el equipo.", "Se recomienda fortalecer sus habilidades de liderazgo para mejorar la direccion y apoyo al equipo."});
        skills.add(new String[]{"Comunicación asertiva", "Se comunica de manera clara, respetuosa y efectiva, facilitando la comprension y el trabajo en equipo.", "Logra transmitir ideas de forma adecuada, aunque puede mejorar la claridad y precision.", "Se sugiere fortalecer la comunicacion asertiva para mejorar la expresion de ideas y la interaccion."});
        skills.add(new String[]{"Gestión del tiempo", "Administra eficientemente su tiempo, priorizando tareas y cumpliendo con los plazos.", "Maneja sus responsabilidades de forma aceptable, aunque puede mejorar la planificacion.", "Se recomienda desarrollar estrategias de organizacion y gestion del tiempo."});
        skills.add(new String[]{"Resolución de conflictos", "Aborda los conflictos de manera constructiva, promoviendo soluciones efectivas.", "Maneja los conflictos de forma adecuada, aunque puede fortalecer su mediacion.", "Se sugiere trabajar en habilidades de resolucion de conflictos."});
        skills.add(new String[]{"Flexibilidad y adaptabilidad", "Se adapta con facilidad a cambios y nuevos entornos, manteniendo un desempeno consistente.", "Muestra disposicion al cambio, aunque puede mejorar su respuesta ante imprevistos.", "Se recomienda fortalecer la adaptabilidad frente a entornos dinamicos."});
        skills.add(new String[]{"Resistencia a la frustración", "Mantiene una actitud positiva ante dificultades, gestionando la presion de forma efectiva.", "Tolera situaciones complejas, aunque puede mejorar su manejo emocional.", "Se sugiere trabajar en el manejo de la frustracion."});
        skills.add(new String[]{"Construcción colectiva", "Participa activamente en el trabajo en equipo, promoviendo la colaboracion.", "Colabora de manera adecuada, aunque puede involucrarse mas.", "Se recomienda fortalecer la participacion en equipo."});
        skills.add(new String[]{"Gestión emocional", "Gestiona sus emociones de manera adecuada, favoreciendo un ambiente equilibrado.", "Reconoce sus emociones, aunque puede mejorar su regulacion.", "Se sugiere desarrollar habilidades de gestion emocional."});
        skills.add(new String[]{"Argumentación", "Expone ideas de manera logica y fundamentada, facilitando la toma de decisiones.", "Presenta argumentos validos, aunque puede mejorar su estructuracion.", "Se recomienda fortalecer la capacidad de argumentacion."});
        skills.add(new String[]{"Desarrollo de sí mismo", "Muestra iniciativa constante por aprender y mejorar, buscando crecimiento continuo.", "Evidencia interes en su desarrollo, aunque puede ser mas constante.", "Se sugiere fomentar habitos de aprendizaje continuo."});
        skills.add(new String[]{"Pensamiento crítico", "Analiza situaciones de manera profunda antes de tomar decisiones.", "Realiza analisis basicos, aunque puede profundizar mas.", "Se recomienda fortalecer el pensamiento critico."});
        skills.add(new String[]{"Orientación a resultados", "Se enfoca en el logro de objetivos con alto nivel de compromiso.", "Cumple con sus responsabilidades, aunque puede mejorar su enfoque.", "Se sugiere desarrollar una mayor orientacion a resultados."});

        try (Connection connection = getConnection()) {
            for (String[] skill : skills) {
                if (!existsByColumn(connection, "skills", "nombre", skill[0])) {
                    try (PreparedStatement statement = connection.prepareStatement("""
                            INSERT INTO skills (nombre, alto, medio, bajo)
                            VALUES (?, ?, ?, ?)
                            """)) {
                        statement.setString(1, skill[0]);
                        statement.setString(2, skill[1]);
                        statement.setString(3, skill[2]);
                        statement.setString(4, skill[3]);
                        statement.executeUpdate();
                    }
                }
            }
        }
    }

    private boolean existsByColumn(Connection connection, String tableName, String columnName, String value) throws SQLException {
        String sql = "SELECT 1 FROM " + tableName + " WHERE " + columnName + " = ? LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void cleanupDuplicates(Connection connection) throws SQLException {
        deleteDuplicates(connection, "admin_users", "email");
        deleteDuplicates(connection, "coders", "email");
        deleteDuplicates(connection, "skills", "nombre");
    }

    private void deleteDuplicates(Connection connection, String tableName, String columnName) throws SQLException {
        String sql = """
                DELETE FROM %s
                WHERE id NOT IN (
                    SELECT MIN(id)
                    FROM %s
                    WHERE %s IS NOT NULL
                    GROUP BY %s
                )
                AND %s IN (
                    SELECT %s
                    FROM %s
                    WHERE %s IS NOT NULL
                    GROUP BY %s
                    HAVING COUNT(*) > 1
                )
                """.formatted(tableName, tableName, columnName, columnName, columnName, columnName, tableName, columnName, columnName);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    private void ensureUniqueConstraint(Connection connection, String tableName, String columnName, String constraintName) throws SQLException {
        String existsSql = """
                SELECT 1
                FROM pg_constraint c
                JOIN pg_class t ON c.conrelid = t.oid
                WHERE t.relname = ?
                AND c.conname = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(existsSql)) {
            statement.setString(1, tableName);
            statement.setString(2, constraintName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return;
                }
            }
        }

        String alterSql = "ALTER TABLE " + tableName + " ADD CONSTRAINT " + constraintName + " UNIQUE (" + columnName + ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(alterSql);
        }
    }
}
