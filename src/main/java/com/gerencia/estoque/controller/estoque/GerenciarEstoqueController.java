package com.gerencia.estoque.controller.estoque;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.estoque.EstoqueProduto;
import com.gerencia.estoque.model.estoque.Produto;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class GerenciarEstoqueController {

    @FXML
    private TableView<EstoqueProduto> tabelaEstoque;

    @FXML
    private TableColumn<EstoqueProduto, String> colunaDescricao;

    @FXML
    private TableColumn<EstoqueProduto, Double> colunaPreco;

    @FXML
    private TableColumn<EstoqueProduto, Integer> colunaQuantidade;

    @FXML
    private TextField campoDescricao;

    @FXML
    private TextField campoPreco;

    @FXML
    private TextField campoQuantidade;

    private ObservableList<EstoqueProduto> listaProdutos = FXCollections.observableArrayList();
    private ObservableList<Produto> listaProdutosCompra = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarProdutos();
    }

    private void configurarTabela() {
        colunaDescricao.setCellValueFactory(dados -> dados.getValue().descricaoProperty());
        colunaPreco.setCellValueFactory(dados -> dados.getValue().precoProperty().asObject());
        colunaQuantidade.setCellValueFactory(dados -> dados.getValue().quantidadeProperty().asObject());

        tabelaEstoque.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                campoDescricao.setText(selecionado.getDescricao());
                campoPreco.setText(String.valueOf(selecionado.getPreco()));
                campoQuantidade.setText(String.valueOf(selecionado.getQuantidade()));
            }
        });
    }

    private void carregarProdutosCompra() {
        String sql = "SELECT * FROM compraprodutos";
        try (Connection connection = Database.getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listaProdutosCompra.add(new Produto(
                        rs.getInt("idprod"),
                        rs.getString("descricao"),
                        rs.getDouble("preco")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void carregarProdutos() {
        String sql = "SELECT * FROM estoque";
        try (Connection connection = Database.getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listaProdutos.add(new EstoqueProduto(
                        rs.getInt("idprod"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                ));
            }
            tabelaEstoque.setItems(listaProdutos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirJanelaSelecionarProduto() {
        Stage janelaSelecaoProduto = new Stage();
        janelaSelecaoProduto.setTitle("Selecionar Produto");

        // Criar uma tabela para mostrar os produtos
        TableView<Produto> tabelaProdutosCompra = new TableView<>();
        TableColumn<Produto, String> colunaDescricaoCompra = new TableColumn<>("Descrição");
        colunaDescricaoCompra.setCellValueFactory(dados -> dados.getValue().descricaoProperty());

        TableColumn<Produto, Double> colunaPrecoCompra = new TableColumn<>("Preço");
        colunaPrecoCompra.setCellValueFactory(dados -> dados.getValue().precoProperty().asObject());

        tabelaProdutosCompra.getColumns().addAll(colunaDescricaoCompra, colunaPrecoCompra);
        carregarProdutosCompra(); // Carregar os produtos da tabela compraprodutos
        tabelaProdutosCompra.setItems(listaProdutosCompra);

        // Definir ação quando um produto for selecionado
        tabelaProdutosCompra.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                // Passar os dados selecionados para os campos
                campoDescricao.setText(selecionado.getDescricao());
                campoPreco.setText(String.valueOf(selecionado.getPreco()));

                // Fechar a janela após a seleção
                janelaSelecaoProduto.close();
            }
        });

        // Exibir a janela
        Scene cena = new Scene(tabelaProdutosCompra);
        janelaSelecaoProduto.setScene(cena);
        janelaSelecaoProduto.initModality(Modality.APPLICATION_MODAL);
        janelaSelecaoProduto.showAndWait();
    }


    @FXML
    private void adicionarProduto() {
        String descricao = campoDescricao.getText();
        double preco = Double.parseDouble(campoPreco.getText());
        int quantidade = Integer.parseInt(campoQuantidade.getText());

        String sql = "INSERT INTO estoque (descricao, preco, quantidade) VALUES (?, ?, ?)";

        try (Connection connection = Database.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, descricao);
            ps.setDouble(2, preco);
            ps.setInt(3, quantidade);
            ps.executeUpdate();
            exibirAlerta("Produto adicionado com sucesso!");
            recarregarProdutos();
        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro ao adicionar produto.");
        }
    }

    @FXML
    private void editarProduto() {
        EstoqueProduto produtoSelecionado = tabelaEstoque.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            exibirAlerta("Selecione um produto para editar.");
            return;
        }

        String descricao = campoDescricao.getText();
        double preco = Double.parseDouble(campoPreco.getText());
        int quantidade = Integer.parseInt(campoQuantidade.getText());

        String sql = "UPDATE estoque SET descricao = ?, preco = ?, quantidade = ? WHERE idprod = ?";

        try (Connection connection = Database.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, descricao);
            ps.setDouble(2, preco);
            ps.setInt(3, quantidade);
            ps.setInt(4, produtoSelecionado.getIdProd());
            ps.executeUpdate();
            exibirAlerta("Produto editado com sucesso!");
            recarregarProdutos();
        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro ao editar produto.");
        }
    }


    @FXML
    private void removerProduto() {
        EstoqueProduto produtoSelecionado = tabelaEstoque.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            exibirAlerta("Selecione um produto para remover.");
            return;
        }

        String sql = "DELETE FROM estoque WHERE idprod = ?";

        try (Connection connection = Database.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, produtoSelecionado.getIdProd());
            ps.executeUpdate();
            exibirAlerta("Produto removido com sucesso!");
            recarregarProdutos();
        } catch (SQLException e) {
            e.printStackTrace();
            exibirAlerta("Erro ao remover produto.");
        }
    }


    private void recarregarProdutos() {
        listaProdutos.clear();
        carregarProdutos();
    }

    private void exibirAlerta(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informação");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        Stage stage = (Stage) tabelaEstoque.getScene().getWindow();
        alerta.initOwner(stage);
        alerta.initModality(Modality.APPLICATION_MODAL);

        alerta.showAndWait();
    }

    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) tabelaEstoque.getScene().getWindow();
        stage.close();
    }
}
