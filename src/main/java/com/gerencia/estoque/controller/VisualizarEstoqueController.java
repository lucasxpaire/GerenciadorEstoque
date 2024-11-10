package com.gerencia.estoque.controller;

import com.gerencia.estoque.dao.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisualizarEstoqueController {

    @FXML
    private TableView<ItemEstoque> tableViewEstoque;

    @FXML
    private TableColumn<ItemEstoque, String> colNomeProduto;

    @FXML
    private TableColumn<ItemEstoque, String> colCategoria;

    @FXML
    private TableColumn<ItemEstoque, Integer> colQuantidade;

    @FXML
    private TableColumn<ItemEstoque, Double> colPreco;

    @FXML
    private TableColumn<ItemEstoque, Integer> colId;

    private ObservableList<ItemEstoque> itensEstoque = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarItensEstoque();

        // Centralizar as células da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        // Centralizando o texto das colunas
        colId.setCellFactory(tc -> new TableCell<ItemEstoque, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
                setAlignment(Pos.CENTER); // Centraliza o conteúdo
            }
        });

        colNomeProduto.setCellFactory(tc -> new TableCell<ItemEstoque, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                }
                setAlignment(Pos.CENTER); // Centraliza o conteúdo
            }
        });

        colCategoria.setCellFactory(tc -> new TableCell<ItemEstoque, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                }
                setAlignment(Pos.CENTER); // Centraliza o conteúdo
            }
        });

        colQuantidade.setCellFactory(tc -> new TableCell<ItemEstoque, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
                setAlignment(Pos.CENTER); // Centraliza o conteúdo
            }
        });

        colPreco.setCellFactory(tc -> new TableCell<ItemEstoque, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item)); // Formata o preço
                }
                setAlignment(Pos.CENTER); // Centraliza o conteúdo
            }
        });

        // Definindo os itens da tabela
        tableViewEstoque.setItems(itensEstoque);
    }


    public void carregarItensEstoque() {
        // Incluindo a coluna 'id' na consulta
        String sql = "SELECT id, nome_produto, categoria_produto, preco_produto, quantidade FROM estoque";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            itensEstoque.clear(); // Limpar a lista antes de adicionar os novos itens

            while (rs.next()) {
                int id = rs.getInt("id");  // Agora, a coluna 'id' é obtida corretamente
                String nomeProduto = rs.getString("nome_produto");
                String categoriaProduto = rs.getString("categoria_produto");
                double precoProduto = rs.getDouble("preco_produto");
                int quantidade = rs.getInt("quantidade");

                // Adicionando o item na lista
                itensEstoque.add(new ItemEstoque(id, nomeProduto, categoriaProduto, quantidade, precoProduto));
            }

            // Atualizando a TableView com os itens do estoque
            tableViewEstoque.setItems(itensEstoque);

        } catch (SQLException e) {
            showAlert("Erro", "Erro ao carregar o estoque: " + e.getMessage());
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
