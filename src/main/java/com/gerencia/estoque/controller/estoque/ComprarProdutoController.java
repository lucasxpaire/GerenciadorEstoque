package com.gerencia.estoque.controller.estoque;
import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.estoque.Produto;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ComprarProdutoController {

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, String> colunaNome;

    @FXML
    private TableColumn<Produto, Double> colunaPreco;

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoPreco;

    @FXML
    private TextField campoQuantidade;

    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarProdutos();
    }

    private void configurarTabela() {
        colunaNome.setCellValueFactory(dados -> dados.getValue().descricaoProperty());
        colunaPreco.setCellValueFactory(dados -> dados.getValue().precoProperty().asObject());

        // Centralizar valores na coluna Nome
        colunaNome.setCellFactory(coluna -> new TableCell<>() {
            @Override
            protected void updateItem(String nome, boolean empty) {
                super.updateItem(nome, empty);
                if (empty || nome == null) {
                    setText(null);
                } else {
                    setText(nome);
                }
                setStyle("-fx-alignment: CENTER;"); // Centralizar texto
            }
        });

        // Centralizar valores na coluna Preço
        colunaPreco.setCellFactory(coluna -> new TableCell<>() {
            @Override
            protected void updateItem(Double preco, boolean empty) {
                super.updateItem(preco, empty);
                if (empty || preco == null) {
                    setText(null);
                } else {
                    setText(String.format("R$ %.2f", preco)); // Formatar como valor monetário
                }
                setStyle("-fx-alignment: CENTER;"); // Centralizar texto
            }
        });

        tabelaProdutos.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                campoNome.setText(selecionado.getDescricao());
                campoPreco.setText(String.valueOf(selecionado.getPreco()));
                campoQuantidade.setText("1");
            }
        });
    }

    private void carregarProdutos() {
        // Conexão com o banco para obter os produtos
        String sql = "SELECT * FROM produto";
        try (Connection connection = Database.getConnection(); Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaProdutos.add(new Produto(rs.getInt("idprod"), rs.getString("descricao"), rs.getDouble("preco")));
            }

            tabelaProdutos.setItems(listaProdutos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void incrementarQuantidade() {
        int quantidade = Integer.parseInt(campoQuantidade.getText());
        campoQuantidade.setText(String.valueOf(quantidade + 1));
    }

    @FXML
    private void decrementarQuantidade() {
        int quantidade = Integer.parseInt(campoQuantidade.getText());
        if (quantidade > 1) {
            campoQuantidade.setText(String.valueOf(quantidade - 1));
        }
    }

    @FXML
    private void confirmarCompra() {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            exibirAlerta("Selecione um produto para continuar.");
            return;
        }

        int quantidade = Integer.parseInt(campoQuantidade.getText());
        String descricao = produtoSelecionado.getDescricao();
        double preco = produtoSelecionado.getPreco();

        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);

            // Inserir em Compra
            String sqlCompra = "INSERT INTO compra (DataHora) VALUES (?) RETURNING idcompra";
            int idCompra;
            try (PreparedStatement psCompra = connection.prepareStatement(sqlCompra)) {
                psCompra.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                ResultSet rs = psCompra.executeQuery();
                rs.next();
                idCompra = rs.getInt("idcompra");
            }

            // Inserir em CompraProdutos
            String sqlCompraProdutos = "INSERT INTO compraprodutos (idprod, idcompra, quantidade, descricao, preco) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psCompraProdutos = connection.prepareStatement(sqlCompraProdutos)) {
                psCompraProdutos.setInt(1, produtoSelecionado.getIdProd());
                psCompraProdutos.setInt(2, idCompra);
                psCompraProdutos.setInt(3, quantidade);
                psCompraProdutos.setString(4, descricao);
                psCompraProdutos.setDouble(5, preco);
                psCompraProdutos.executeUpdate();
            }

            connection.commit();

            exibirAlerta("Compra realizada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro ao realizar a compra. Tente novamente.");
        }
    }

    private void exibirAlerta(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informação");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        // Obtendo o Stage da janela principal
        Stage stage = (Stage) tabelaProdutos.getScene().getWindow();

        // Definindo a janela de alerta para ser sobre a janela principal
        alerta.initOwner(stage);
        alerta.initModality(Modality.APPLICATION_MODAL);  // Garantir que o alerta é modal

        alerta.showAndWait();
    }

    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) tabelaProdutos.getScene().getWindow();
        stage.close();
    }
}
