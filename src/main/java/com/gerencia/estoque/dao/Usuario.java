package com.gerencia.estoque.dao;

import com.gerencia.estoque.dao.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {

    public boolean verificarCredenciais(String nomeUsuario, String senha) {
        String sql = "SELECT * FROM usuarios WHERE nome_usuario = ? AND senha = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nomeUsuario);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Se houver um resultado, as credenciais estão corretas

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
