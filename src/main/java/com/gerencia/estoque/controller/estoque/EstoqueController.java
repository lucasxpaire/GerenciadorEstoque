package com.gerencia.estoque.controller.estoque;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.estoque.Estoque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class EstoqueController {

    @FXML
    private TableView<Estoque> tabelaEstoque;

    @FXML
    private TableColumn<Estoque, String> colunaDescricao;

    @FXML
    private TableColumn<Estoque, Double> colunaPreco;

    @FXML
    private TableColumn<Estoque, Integer> colunaQuantidade;

    private ObservableList<Estoque> listaEstoque;

    @FXML
    public void initialize() {
        configurarTabela();
        carregarProdutosEstoque();
    }

    // Dentro do método configurarTabela()
    private void configurarTabela() {
        // Configurações das colunas da tabela
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        // Alinhamento no centro para a coluna Descrição
        colunaDescricao.setCellFactory(column -> {
            return new TableCell<Estoque, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };
        });

        // Alinhar o preço ao centro
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaPreco.setCellFactory(column -> {
            return new TableCell<Estoque, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(String.format("%.2f", item)); // Formata o preço com 2 casas decimais
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };
        });

        // Alinhar a quantidade ao centro
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaQuantidade.setCellFactory(column -> {
            return new TableCell<Estoque, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item));
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };
        });

        // Inicializa a lista e associa à tabela
        listaEstoque = FXCollections.observableArrayList();
        tabelaEstoque.setItems(listaEstoque);
    }


    private void carregarProdutosEstoque() {
        listaEstoque.clear();
        String sql = "SELECT p.IdProduto, p.Descricao, p.Preco, e.Quantidade " +
                "FROM Produto p " +
                "JOIN Estoque e ON p.IdProduto = e.IdProduto";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estoque produto = new Estoque(
                        rs.getInt("IdProduto"),
                        rs.getDouble("Preco"),
                        rs.getString("Descricao"),
                        rs.getInt("Quantidade")
                );
                listaEstoque.add(produto);
            }
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao carregar os produtos do estoque: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirComprarMaisQuantidade(ActionEvent event) {
        try {
            Stage janela = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/gerencia/estoque/estoque/comprar-mais-quantidade.fxml"));
            Scene cena = new Scene(root);
            janela.setScene(cena);
            janela.setTitle("Comprar Mais Quantidade");
            janela.initModality(javafx.stage.Modality.WINDOW_MODAL);
            janela.initOwner(((javafx.scene.Node) event.getSource()).getScene().getWindow());
            janela.showAndWait();
            carregarProdutosEstoque();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao abrir a tela de Compra de Mais Quantidade: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirAdicionarNovoItem(ActionEvent event) {
        try {
            Stage janela = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/gerencia/estoque/estoque/adicionar-novo-item.fxml"));
            Scene cena = new Scene(root);
            janela.setScene(cena);
            janela.setTitle("Adicionar Novo Item");
            janela.initModality(javafx.stage.Modality.WINDOW_MODAL);
            janela.initOwner(((javafx.scene.Node) event.getSource()).getScene().getWindow());
            janela.showAndWait();
            carregarProdutosEstoque();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao abrir a tela de Adicionar Novo Item: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-prop/painel-prop.fxml", "Painel do Proprietário", event);
    }

    private void carregarTela(String caminhoFXML, String titulo, ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Falha ao carregar a tela: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
