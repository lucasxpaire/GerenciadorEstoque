package com.gerencia.estoque.controller.estoque;

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

public class ComprarItemController {

    @FXML
    private TextField nomeProduto;

    @FXML
    private TextField categoriaProduto;

    @FXML
    private TextField precoProduto;

    @FXML
    private TextField quantidadeProduto;

    @FXML
    public void comprarItem(ActionEvent event) {
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

        // Inserir na tabela itens_comprados
        String sqlCompra = "INSERT INTO itens_comprados (nome_produto, categoria_produto, preco_produto, quantidade_comprada) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            // Inserir o item comprado na tabela itens_comprados
            try (PreparedStatement stmtCompra = conn.prepareStatement(sqlCompra)) {
                stmtCompra.setString(1, nome);
                stmtCompra.setString(2, categoria);
                stmtCompra.setDouble(3, preco);
                stmtCompra.setInt(4, quantidade);

                int rowsAffectedCompra = stmtCompra.executeUpdate();

                if (rowsAffectedCompra > 0) {
                    showAlert("Sucesso", "Compra registrada com sucesso.");
                    limparCampos();
                } else {
                    showAlert("Erro", "Falha ao registrar a compra.");
                }
            }

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao acessar o banco de dados: " + e.getMessage());
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

    private void limparCampos() {
        nomeProduto.clear();
        categoriaProduto.clear();
        precoProduto.clear();
        quantidadeProduto.clear();
    }

    @FXML
    public void voltarPainelAdm(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/estoque/manter-estoque.fxml", "Painel do Administrador", event);
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
