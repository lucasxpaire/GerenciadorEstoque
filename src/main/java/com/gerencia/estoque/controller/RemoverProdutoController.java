package com.gerencia.estoque.controller;

import com.gerencia.estoque.dao.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoverProdutoController {

    @FXML
    private TextField nomeProduto;

    @FXML
    public void removerProduto(ActionEvent event) {
        String nome = nomeProduto.getText();

        // Validação do campo
        if (nome.isEmpty()) {
            showAlert("Erro", "O nome do produto é obrigatório.");
            return;
        }

        // Remover produto do banco de dados
        String sql = "DELETE FROM produtos WHERE nome = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Sucesso", "Produto removido com sucesso.");
                nomeProduto.clear();  // Limpar o campo de texto após remoção
            } else {
                showAlert("Erro", "Produto não encontrado.");
            }

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao acessar o banco de dados: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void voltarPainelAdm(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/manter-estoque.fxml", "Painel do Administrador", event);
    }

    // Método para carregar a tela
    private void carregarTela(String caminhoFXML, String titulo, ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            showAlert("Erro", "Falha ao carregar a tela: " + e.getMessage());
        }
    }
}
