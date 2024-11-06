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

public class CadastroProdutoController {

    @FXML
    private TextField nomeProduto;

    @FXML
    private TextField categoriaProduto;

    @FXML
    private TextField precoProduto;

    @FXML
    private TextField quantidadeProduto;

    @FXML
    public void cadastrarProduto(ActionEvent event) {
        String nome = nomeProduto.getText();
        String categoria = categoriaProduto.getText();
        String precoStr = precoProduto.getText();
        String quantidadeStr = quantidadeProduto.getText();

        // Validação dos campos
        if (nome.isEmpty() || categoria.isEmpty() || precoStr.isEmpty() || quantidadeStr.isEmpty()) {
            showAlert("Erro", "Todos os campos são obrigatórios.");
            return;
        }

        // Converta os campos de preço e quantidade
        double preco;
        int quantidade;

        try {
            preco = Double.parseDouble(precoStr);
            quantidade = Integer.parseInt(quantidadeStr);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Preço e Quantidade devem ser números válidos.");
            return;
        }

        // Inserir no banco de dados
        String sql = "INSERT INTO produtos (nome, categoria, preco, quantidade) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, categoria);
            stmt.setDouble(3, preco);
            stmt.setInt(4, quantidade);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Sucesso", "Produto cadastrado com sucesso.");
                limparCampos();
            } else {
                showAlert("Erro", "Falha ao cadastrar o produto.");
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

    private void limparCampos() {
        nomeProduto.clear();
        categoriaProduto.clear();
        precoProduto.clear();
        quantidadeProduto.clear();
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    // Método para voltar ao painel de administração
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
            mostrarAlerta("Erro", "Falha ao carregar a tela: " + e.getMessage());
        }
    }
}

