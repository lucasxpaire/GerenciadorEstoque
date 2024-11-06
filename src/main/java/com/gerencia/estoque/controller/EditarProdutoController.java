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
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditarProdutoController {

    @FXML
    private TextField nomeProdutoBusca;
    @FXML
    private TextField nomeProduto;
    @FXML
    private TextField categoriaProduto;
    @FXML
    private TextField precoProduto;
    @FXML
    private TextField quantidadeProduto;

    // Método para buscar o produto no banco de dados
    @FXML
    public void buscarProduto(ActionEvent event) {
        String nomeBusca = nomeProdutoBusca.getText();

        // Verificar se o nome do produto foi informado
        if (nomeBusca.isEmpty()) {
            showAlert("Erro", "Digite o nome do produto para buscar.");
            return;
        }

        // Consulta SQL para buscar o produto
        String sql = "SELECT * FROM produtos WHERE nome = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeBusca);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Preencher os campos com os dados do produto encontrado
                nomeProduto.setText(rs.getString("nome"));
                categoriaProduto.setText(rs.getString("categoria"));
                precoProduto.setText(String.valueOf(rs.getDouble("preco")));
                quantidadeProduto.setText(String.valueOf(rs.getInt("quantidade")));
            } else {
                showAlert("Erro", "Produto não encontrado.");
            }

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao acessar o banco de dados: " + e.getMessage());
        }
    }

    // Método para salvar as alterações no banco de dados
    @FXML
    public void salvarProduto(ActionEvent event) {
        String nome = nomeProduto.getText();
        String categoria = categoriaProduto.getText();
        String preco = precoProduto.getText();
        String quantidade = quantidadeProduto.getText();

        // Validar se os campos estão preenchidos
        if (nome.isEmpty() || categoria.isEmpty() || preco.isEmpty() || quantidade.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        // Atualizar as informações do produto no banco de dados
        String sql = "UPDATE produtos SET nome = ?, categoria = ?, preco = ?, quantidade = ? WHERE nome = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, categoria);
            stmt.setDouble(3, Double.parseDouble(preco));
            stmt.setInt(4, Integer.parseInt(quantidade));
            stmt.setString(5, nomeProdutoBusca.getText());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Sucesso", "Produto atualizado com sucesso.");
            } else {
                showAlert("Erro", "Produto não encontrado para atualização.");
            }

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao atualizar o produto: " + e.getMessage());
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
