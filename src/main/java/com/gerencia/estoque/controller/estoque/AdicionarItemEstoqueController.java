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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

public class AdicionarItemEstoqueController {

    @FXML
    private ComboBox<ItemComprado> comboBoxItensComprados; // ComboBox para selecionar itens comprados

    @FXML
    private TextField quantidadeProduto; // Campo para informar a quantidade a ser adicionada ao estoque

    @FXML
    private Label quantidadeDisponivelLabel; // Rótulo para mostrar a quantidade disponível

    private ObservableList<ItemComprado> itensComprados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarItensComprados();

        // Atualiza a quantidade disponível ao selecionar um item na ComboBox
        comboBoxItensComprados.setOnAction(event -> atualizarQuantidadeDisponivel());
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
            comboBoxItensComprados.setItems(itensComprados);

            // Exibir o nome do item na ComboBox
            comboBoxItensComprados.setCellFactory(listView -> new javafx.scene.control.ListCell<ItemComprado>() {
                @Override
                protected void updateItem(ItemComprado item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNome());
                    }
                }
            });

            comboBoxItensComprados.setButtonCell(new javafx.scene.control.ListCell<ItemComprado>() {
                @Override
                protected void updateItem(ItemComprado item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNome());
                    }
                }
            });

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao carregar os itens comprados: " + e.getMessage());
        }
    }

    // Atualizar a quantidade disponível ao selecionar um item
    private void atualizarQuantidadeDisponivel() {
        ItemComprado selectedItem = comboBoxItensComprados.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int quantidadeDisponivel = selectedItem.getQuantidade();
            quantidadeDisponivelLabel.setText("Quantidade disponível: " + quantidadeDisponivel);
        }
    }

    // Método para adicionar item ao estoque
    @FXML
    public void adicionarAoEstoque(ActionEvent event) {
        ItemComprado selectedItem = comboBoxItensComprados.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Erro", "Selecione um item para adicionar ao estoque.");
            return;
        }

        // Definindo os dados a serem inseridos
        String nome = selectedItem.getNome();
        String categoria = selectedItem.getCategoria();
        double preco = selectedItem.getPreco();

        // Verificando a quantidade do item a ser adicionada
        String quantidadeStr = quantidadeProduto.getText();
        if (quantidadeStr.isEmpty()) {
            showAlert("Erro", "Por favor, insira a quantidade.");
            return;
        }

        int quantidadeAdicionar;
        try {
            quantidadeAdicionar = Integer.parseInt(quantidadeStr);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Quantidade deve ser um número válido.");
            return;
        }

        // Verificar se a quantidade é válida (não pode ser 0 ou negativa)
        if (quantidadeAdicionar <= 0) {
            showAlert("Erro", "A quantidade deve ser maior que 0.");
            return;
        }

        // Verificar se há quantidade suficiente de itens comprados para adicionar ao estoque
        int quantidadeComprada = selectedItem.getQuantidade();
        if (quantidadeAdicionar > quantidadeComprada) {
            showAlert("Erro", "Não há quantidade suficiente de itens comprados.");
            return;
        }

        // Inserir item no estoque
        String sqlInserirEstoque = "INSERT INTO estoque (nome_produto, categoria_produto, preco_produto, quantidade) VALUES (?, ?, ?, ?)";
        String sqlDecrementarComprado = "UPDATE itens_comprados SET quantidade_comprada = quantidade_comprada - ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtInserirEstoque = conn.prepareStatement(sqlInserirEstoque);
             PreparedStatement stmtDecrementarComprado = conn.prepareStatement(sqlDecrementarComprado)) {

            // Inserir item no estoque
            stmtInserirEstoque.setString(1, nome);
            stmtInserirEstoque.setString(2, categoria);
            stmtInserirEstoque.setBigDecimal(3, BigDecimal.valueOf(preco)); // Usando BigDecimal para precisão
            stmtInserirEstoque.setInt(4, quantidadeAdicionar);

            // Executar a inserção no estoque
            int rowsAffectedEstoque = stmtInserirEstoque.executeUpdate();

            if (rowsAffectedEstoque > 0) {
                // Decrementar a quantidade de itens comprados
                stmtDecrementarComprado.setInt(1, quantidadeAdicionar);
                stmtDecrementarComprado.setInt(2, selectedItem.getId());

                int rowsAffectedComprado = stmtDecrementarComprado.executeUpdate();

                if (rowsAffectedComprado > 0) {
                    showAlert("Sucesso", "Item adicionado ao estoque com sucesso e quantidade comprada atualizada.");

                    // Limpar os campos após a adição do item
                    quantidadeProduto.clear();
                    comboBoxItensComprados.getSelectionModel().clearSelection();

                    // Limpar o campo de quantidade disponível
                    quantidadeDisponivelLabel.setText("Quantidade disponível: ");

                    // Recarregar os itens comprados para garantir que a quantidade seja atualizada
                    carregarItensComprados();

                    // Atualizar a quantidade disponível do item selecionado
                    atualizarQuantidadeDisponivel();

                } else {
                    showAlert("Erro", "Falha ao atualizar a quantidade dos itens comprados.");
                }
            } else {
                showAlert("Erro", "Falha ao adicionar o item ao estoque.");
            }

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao acessar o banco de dados: " + e.getMessage());
        }
    }


    // Exibir um alerta para o usuário
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para voltar para a tela do painel de administração
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
