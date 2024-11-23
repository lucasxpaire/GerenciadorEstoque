package com.gerencia.estoque.controller.estoque;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.estoque.EstoqueProduto;
import com.gerencia.estoque.model.estoque.Produto;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
        // Criar a nova janela de seleção de produto
        Stage janelaSelecaoProduto = new Stage();
        janelaSelecaoProduto.setTitle("Selecionar Produto");

        // Criar uma tabela para mostrar os produtos
        TableView<Produto> tabelaProdutosCompra = new TableView<>();
        TableColumn<Produto, String> colunaDescricaoCompra = new TableColumn<>("Descrição");
        colunaDescricaoCompra.setCellValueFactory(dados -> dados.getValue().descricaoProperty());

        TableColumn<Produto, Double> colunaPrecoCompra = new TableColumn<>("Preço");
        colunaPrecoCompra.setCellValueFactory(dados -> dados.getValue().precoProperty().asObject());

        tabelaProdutosCompra.getColumns().addAll(colunaDescricaoCompra, colunaPrecoCompra);

        // Carregar os produtos da tabela "compraprodutos"
        carregarProdutosCompra(); // Método que preenche a lista listaProdutosCompra
        tabelaProdutosCompra.setItems(listaProdutosCompra);

        // Ação ao selecionar um produto
        tabelaProdutosCompra.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                campoDescricao.setText(selecionado.getDescricao());
                campoPreco.setText(String.valueOf(selecionado.getPreco()));

                // Fechar a janela após a seleção
                janelaSelecaoProduto.close();
            }
        });

        // Criar um botão para adicionar um novo produto
        Button btnAdicionarProduto = new Button("Adicionar Produto");
        btnAdicionarProduto.setOnAction(e -> abrirJanelaAdicionarProduto());

        // Layout para organizar a tabela e o botão
        VBox vbox = new VBox(tabelaProdutosCompra, btnAdicionarProduto);
        Scene cena = new Scene(vbox);
        janelaSelecaoProduto.setScene(cena);

        // Configurar a janela para sempre aparecer acima da janela principal
        Stage stageAtual = (Stage) tabelaEstoque.getScene().getWindow();
        janelaSelecaoProduto.initOwner(stageAtual); // A janela selecionada será dona da nova janela
        janelaSelecaoProduto.initModality(Modality.APPLICATION_MODAL); // Modal, impede interações com outras janelas
        janelaSelecaoProduto.show();
    }

    private void abrirJanelaAdicionarProduto() {
        // Criar a nova janela de adicionar produto
        Stage janelaAdicionarProduto = new Stage();
        janelaAdicionarProduto.setTitle("Adicionar Produto");

        // Campos de entrada para o novo produto
        TextField campoDescricaoNovoProduto = new TextField();
        campoDescricaoNovoProduto.setPromptText("Descrição");

        TextField campoPrecoNovoProduto = new TextField();
        campoPrecoNovoProduto.setPromptText("Preço");

        TextField campoQuantidadeNovoProduto = new TextField();
        campoQuantidadeNovoProduto.setPromptText("Quantidade");

        // Botão para adicionar o produto ao estoque
        Button btnAdicionarNovoProduto = new Button("Adicionar");
        btnAdicionarNovoProduto.setOnAction(e -> {
            String descricao = campoDescricaoNovoProduto.getText();
            double preco = Double.parseDouble(campoPrecoNovoProduto.getText());
            int quantidade = Integer.parseInt(campoQuantidadeNovoProduto.getText());

            // Adicionar o produto no banco de dados
            adicionarProdutoAoEstoque(descricao, preco, quantidade);

            // Fechar a janela após adicionar
            janelaAdicionarProduto.close();
        });

        // Layout da janela de adicionar produto
        VBox vboxAdicionarProduto = new VBox(campoDescricaoNovoProduto, campoPrecoNovoProduto, campoQuantidadeNovoProduto, btnAdicionarNovoProduto);
        Scene cenaAdicionarProduto = new Scene(vboxAdicionarProduto);
        janelaAdicionarProduto.setScene(cenaAdicionarProduto);

        // Exibir a janela de adicionar produto
        janelaAdicionarProduto.initModality(Modality.APPLICATION_MODAL);
        janelaAdicionarProduto.show();
    }

    private void adicionarProdutoAoEstoque(String descricao, double preco, int quantidade) {
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
