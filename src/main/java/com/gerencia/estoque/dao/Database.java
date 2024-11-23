package com.gerencia.estoque.dao;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5433/estoque";
    private static final String USER = "postgres";
    private static final String PASSWORD = "lucas";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static boolean areCredentialsConfigured() {
        try (Connection connection = getConnection()) {
            String sql = "SELECT COUNT(*) FROM credenciais WHERE usuario = ? AND senha = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "prop");
                statement.setString(2, "prop123");
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}