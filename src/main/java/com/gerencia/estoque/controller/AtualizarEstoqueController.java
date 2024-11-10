package com.gerencia.estoque.controller;

import com.gerencia.estoque.dao.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AtualizarEstoqueController {

    public void atualizarEstoque(ActionEvent event) {
        String sqlBuscarCompras = "SELECT produto_id, SUM(quantidade_comprada) AS total_comprado FROM itens_comprados GROUP BY produto_id";
        String sqlAtualizarEstoque = "UPDATE produtos SET quantidade = quantidade + ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtBuscarCompras = conn.prepareStatement(sqlBuscarCompras);
             ResultSet rs = stmtBuscarCompras.executeQuery()) {

            while (rs.next()) {
                int produtoId = rs.getInt("produto_id");
                int totalComprado = rs.getInt("total_comprado");

                try (PreparedStatement stmtAtualizarEstoque = conn.prepareStatement(sqlAtualizarEstoque)) {
                    stmtAtualizarEstoque.setInt(1, totalComprado);
                    stmtAtualizarEstoque.setInt(2, produtoId);

                    stmtAtualizarEstoque.executeUpdate();
                }
            }

            showAlert("Sucesso", "Estoque atualizado com sucesso.");
        } catch (SQLException e) {
            showAlert("Erro", "Erro ao atualizar o estoque: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
