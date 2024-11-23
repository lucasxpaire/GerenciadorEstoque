package com.gerencia.estoque.controller.estoque;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.estoque.ItemEstoque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoverItemEstoqueController {

    @FXML
    private ComboBox<ItemEstoque> comboBoxItens; // ComboBox para selecionar os itens do estoque

    @FXML
    private TextField nomeProduto;

    @FXML
    private TextField quantidadeProduto;

    @FXML
    private TextField categoriaProduto;

    @FXML
    private TextField precoProduto;

    @FXML
    private TextField quantidadeRemoverProduto;

    private ObservableList<ItemEstoque> itensEstoque = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarItensEstoque();
    }

    // Carregar itens do estoque na ComboBox
    private void carregarItensEstoque() {
        String sql = "SELECT * FROM estoque";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            itensEstoque.clear(); // Limpar a lista antes de adicionar os novos itens

            while (rs.next()) {
                ItemEstoque item = new ItemEstoque(
                        rs.getInt("id"),
                        rs.getString("nome_produto"),
                        rs.getString("categoria_produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("preco_produto")
                );
                itensEstoque.add(item);
            }

            // Preencher a ComboBox com os itens do estoque
            comboBoxItens.setItems(itensEstoque);

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao carregar os itens do estoque: " + e.getMessage());
        }
    }

    // Quando o item é selecionado na ComboBox, preenche os campos de detalhes
    @FXML
    public void exibirDetalhesItem() {
        ItemEstoque selectedItem = comboBoxItens.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            nomeProduto.setText(selectedItem.getNome());
            quantidadeProduto.setText(String.valueOf(selectedItem.getQuantidade()));
            categoriaProduto.setText(selectedItem.getCategoria());
            precoProduto.setText(String.valueOf(selectedItem.getPreco())); // Exibir o preço

            // Desabilitar campos para evitar edição
            nomeProduto.setDisable(true);
            quantidadeProduto.setDisable(true);
            categoriaProduto.setDisable(true);
            precoProduto.setDisable(true); // Desabilitar o campo de preço
        }
    }

    // Remover item do estoque
    // Remover item do estoque
    // Remover item do estoque
    @FXML
    public void removerProduto(ActionEvent event) {
        ItemEstoque selectedItem = comboBoxItens.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Erro", "Selecione um item para remover.");
            return;
        }

        // Quantidade a ser removida do estoque
        int quantidadeRemover;
        try {
            quantidadeRemover = Integer.parseInt(quantidadeRemoverProduto.getText());
        } catch (NumberFormatException e) {
            showAlert("Erro", "Quantidade inválida.");
            return;
        }

        if (quantidadeRemover <= 0) {
            showAlert("Erro", "Quantidade a ser removida deve ser maior que zero.");
            return;
        }

        // Verificar se há estoque suficiente
        int quantidadeAtual = selectedItem.getQuantidade();
        if (quantidadeRemover > quantidadeAtual) {
            showAlert("Erro", "Não há estoque suficiente para remover.");
            return;
        }

        // Atualizar a quantidade do item no estoque
        int novaQuantidade = quantidadeAtual - quantidadeRemover;

        // Atualizar no banco de dados
        String sql;
        if (novaQuantidade == 0) {
            // Se a quantidade for zero, remover o item completamente
            sql = "DELETE FROM estoque WHERE id = ?";
        } else {
            // Caso contrário, apenas atualizar a quantidade
            sql = "UPDATE estoque SET quantidade = ? WHERE id = ?";
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (novaQuantidade == 0) {
                stmt.setInt(1, selectedItem.getId()); // Exclui o item
            } else {
                stmt.setInt(1, novaQuantidade); // Atualiza a quantidade
                stmt.setInt(2, selectedItem.getId());
            }

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                if (novaQuantidade == 0) {
                    showAlert("Sucesso", "Item removido com sucesso.");
                } else {
                    showAlert("Sucesso", "Quantidade atualizada com sucesso.");
                }
                carregarItensEstoque(); // Atualizar a lista de itens
                limparCampos(); // Limpar os campos de texto após a operação
            } else {
                showAlert("Erro", "Falha ao remover o item.");
            }

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao acessar o banco de dados: " + e.getMessage());
        }
    }

    // Método para limpar os campos de texto
    private void limparCampos() {
        nomeProduto.clear();
        quantidadeProduto.clear();
        categoriaProduto.clear();
        precoProduto.clear();
        quantidadeRemoverProduto.clear();

        // Habilitar novamente os campos de texto, caso eles tenham sido desabilitados antes
        nomeProduto.setDisable(false);
        quantidadeProduto.setDisable(false);
        categoriaProduto.setDisable(false);
        precoProduto.setDisable(false);
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void voltar(ActionEvent event) {
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
