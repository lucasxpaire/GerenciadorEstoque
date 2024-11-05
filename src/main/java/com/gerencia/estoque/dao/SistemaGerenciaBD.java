package com.gerencia.estoque.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SistemaGerenciaBD {

    private final String url = "jdbc:postgresql://localhost:5432/seu_banco";
    private final String user = "seu_usuario";
    private final String password = "sua_senha";

    // Método para conectar ao banco
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Método para inserir um usuário
    public void adicionarUsuario(String nome, String senha, boolean isProprietario) {
        String sql = "INSERT INTO usuarios(nome, senha, is_proprietario) VALUES (?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, senha);
            pstmt.setBoolean(3, isProprietario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para buscar um usuário
    public void buscarUsuario(String nome) {
        String sql = "SELECT * FROM usuarios WHERE nome = ?";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("nome") + "\t" +
                        rs.getString("senha") + "\t" +
                        rs.getBoolean("is_proprietario"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
