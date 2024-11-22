package com.gerencia.estoque.controller.estoque;

import com.gerencia.estoque.dao.DatabaseConnection;
import com.gerencia.estoque.model.estoque.ItemComprado;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlterarItemController {

    @FXML
    private ComboBox<ItemComprado> comboBoxItens; // ComboBox para selecionar itens

    @FXML
    private TextField nomeProduto;

    @FXML
    private TextField precoProduto;

    private ObservableList<ItemComprado> itensComprados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarItensComprados();
    }

    // Carregar itens comprados na ComboBox
    private void carregarItensComprados() {
        String sql = "SELECT * FROM itens_comprados";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            itensComprados.clear(); // Limpar a lista antes de adicionar os novos itens

            while (rs.next()) {
                ItemComprado item = new ItemComprado(
                        rs.getInt("id"),
                        rs.getString("nome_produto"),
                        rs.getString("categoria_produto"),
                        rs.getDouble("preco_produto"),
                        rs.getInt("quantidade_comprada")
                );
                itensComprados.add(item);
            }

            // Atribuir a lista de itens à ComboBox
            comboBoxItens.setItems(itensComprados);

            // Definir a forma como o item será exibido na ComboBox
            comboBoxItens.setCellFactory(param -> new ListCell<ItemComprado>() {
                @Override
                protected void updateItem(ItemComprado item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNome()); // Exibe apenas o nome do produto
                    }
                }
            });

            // Definir o comportamento ao selecionar um item
            comboBoxItens.setButtonCell(new ListCell<ItemComprado>() {
                @Override
                protected void updateItem(ItemComprado item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNome()); // Exibe apenas o nome do produto
                    }
                }
            });

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao carregar os itens comprados: " + e.getMessage());
        }
    }

    // Quando o item é selecionado na ComboBox, preenche os TextFields
    @FXML
    public void exibirDetalhesItem() {
        ItemComprado selectedItem = comboBoxItens.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            nomeProduto.setText(selectedItem.getNome());
            precoProduto.setText(String.valueOf(selectedItem.getPreco()));
        }
    }

    // Alterar item
    @FXML
    public void alterarProduto(ActionEvent event) {
        ItemComprado selectedItem = comboBoxItens.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Erro", "Selecione um item para alterar.");
            return;
        }

        String nome = nomeProduto.getText();
        String precoStr = precoProduto.getText();

        if (nome.isEmpty() || precoStr.isEmpty()) {
            showAlert("Erro", "Todos os campos são obrigatórios.");
            return;
        }

        double preco;

        try {
            preco = Double.parseDouble(precoStr);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Preço deve ser um número válido.");
            return;
        }

        String sql = "UPDATE itens_comprados SET nome_produto = ?, preco_produto = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setInt(3, selectedItem.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Sucesso", "Item alterado com sucesso.");
                carregarItensComprados(); // Atualizar lista
                limparCampos(); // Limpar campos após a alteração
            } else {
                showAlert("Erro", "Falha ao alterar o item.");
            }

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao acessar o banco de dados: " + e.getMessage());
        }
    }

    // Excluir item
    @FXML
    public void excluirProduto(ActionEvent event) {
        ItemComprado selectedItem = comboBoxItens.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Erro", "Selecione um item para excluir.");
            return;
        }

        String sql = "DELETE FROM itens_comprados WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, selectedItem.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Sucesso", "Item excluído com sucesso.");
                carregarItensComprados(); // Atualizar lista
                limparCampos(); // Limpar campos após exclusão
            } else {
                showAlert("Erro", "Falha ao excluir o item.");
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

    // Limpar os campos de entrada
    private void limparCampos() {
        nomeProduto.clear();
        precoProduto.clear();
        comboBoxItens.getSelectionModel().clearSelection(); // Limpar a seleção da ComboBox
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
