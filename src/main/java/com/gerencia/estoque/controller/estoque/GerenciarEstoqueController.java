package com.gerencia.estoque.controller.estoque;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.estoque.CompraProduto;
import com.gerencia.estoque.model.estoque.EstoqueProduto;
import com.gerencia.estoque.model.estoque.Produto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

    private ObservableList<EstoqueProduto> listaProdutosEstoque = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarProdutosEstoque();
    }

    private void configurarTabela() {
        colunaDescricao.setCellValueFactory(dados -> dados.getValue().descricaoProperty());
        colunaPreco.setCellValueFactory(dados -> dados.getValue().precoProperty().asObject());
        colunaQuantidade.setCellValueFactory(dados -> dados.getValue().quantidadeProperty().asObject());
    }

    private void carregarProdutosEstoque() {
        listaProdutosEstoque.clear();
        String sql = "SELECT * FROM Estoque";
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listaProdutosEstoque.add(new EstoqueProduto(
                        rs.getInt("IdProd"),
                        rs.getString("Descricao"),
                        rs.getDouble("Preco"),
                        rs.getInt("Quantidade")
                ));
            }
            tabelaEstoque.setItems(listaProdutosEstoque);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void adicionarProduto() {
        // Criar a janela para listar os produtos de CompraProdutos
        Stage janelaAdicionarProduto = new Stage();
        janelaAdicionarProduto.setTitle("Adicionar Produto");

        // Criação da tabela de CompraProduto
        TableView<CompraProduto> tabelaCompraProdutos = new TableView<>();
        TableColumn<CompraProduto, String> colunaDescricaoCompra = new TableColumn<>("Descrição");
        colunaDescricaoCompra.setCellValueFactory(dados -> dados.getValue().descricaoProperty());

        TableColumn<CompraProduto, Double> colunaPrecoCompra = new TableColumn<>("Preço");
        colunaPrecoCompra.setCellValueFactory(dados -> dados.getValue().precoProperty().asObject());

        // Adicionando a coluna de quantidade comprada
        TableColumn<CompraProduto, Integer> colunaQuantidadeCompra = new TableColumn<>("Quantidade Comprada");
        colunaQuantidadeCompra.setCellValueFactory(dados -> dados.getValue().quantidadeProperty().asObject());

        tabelaCompraProdutos.getColumns().addAll(colunaDescricaoCompra, colunaPrecoCompra, colunaQuantidadeCompra);

        ObservableList<CompraProduto> listaCompraProdutos = FXCollections.observableArrayList();
        carregarProdutosCompra(listaCompraProdutos);
        tabelaCompraProdutos.setItems(listaCompraProdutos);

        // Spinner ou campo de texto para quantidade comprada
        Label labelQuantidade = new Label("Quantidade:");
        Spinner<Integer> spinnerQuantidade = new Spinner<>(1, Integer.MAX_VALUE, 1);
        spinnerQuantidade.setEditable(true);

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setOnAction(e -> {
            CompraProduto produtoSelecionado = tabelaCompraProdutos.getSelectionModel().getSelectedItem();
            if (produtoSelecionado != null) {
                int quantidadeComprada = spinnerQuantidade.getValue();  // Captura a quantidade inserida
                int quantidadeDisponivel = produtoSelecionado.getQuantidade();  // Quantidade disponível na CompraProdutos

                if (quantidadeComprada > quantidadeDisponivel) {
                    exibirAlerta("Quantidade maior do que a disponível no produto.");
                } else if (quantidadeComprada > 0) {
                    // Adiciona o produto ao estoque
                    adicionarProdutoAoEstoque(produtoSelecionado, quantidadeComprada);
                    // Decrementa a quantidade em CompraProdutos
                    atualizarQuantidadeCompra(produtoSelecionado, quantidadeComprada);

                    janelaAdicionarProduto.close();
                    exibirAlerta("Produto adicionado ao estoque com sucesso!");
                } else {
                    exibirAlerta("Por favor, insira uma quantidade válida.");
                }
            } else {
                exibirAlerta("Selecione um produto antes de confirmar.");
            }
        });

        VBox layout = new VBox(tabelaCompraProdutos, labelQuantidade, spinnerQuantidade, btnConfirmar);
        Scene cena = new Scene(layout);

        janelaAdicionarProduto.setScene(cena);
        janelaAdicionarProduto.initOwner(tabelaEstoque.getScene().getWindow());
        janelaAdicionarProduto.showAndWait();
    }

    private void carregarProdutosCompra(ObservableList<CompraProduto> lista) {
        String sql = "SELECT p.IdProd, cp.IdCompra, cp.Quantidade, p.Descricao, p.Preco " +
                "FROM CompraProdutos cp " +
                "JOIN Produto p ON cp.IdProd = p.IdProd";  // Ajuste para incluir a quantidade de compra

        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new CompraProduto(
                        rs.getInt("IdProd"),
                        rs.getInt("IdCompra"),
                        rs.getInt("Quantidade"),
                        rs.getString("Descricao"),
                        rs.getDouble("Preco")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void adicionarProdutoAoEstoque(CompraProduto produto, int quantidade) {
        // Atualizar a quantidade do produto no estoque se já existir
        String sqlSelect = "SELECT Quantidade FROM Estoque WHERE idprod = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement psSelect = connection.prepareStatement(sqlSelect)) {
            psSelect.setInt(1, produto.getIdProd());
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                // Produto já existe, atualizar quantidade
                int quantidadeAtual = rs.getInt("Quantidade");
                int novaQuantidade = quantidadeAtual + quantidade;

                String sqlUpdate = "UPDATE Estoque SET Quantidade = ? WHERE idprod = ?";
                try (PreparedStatement psUpdate = connection.prepareStatement(sqlUpdate)) {
                    psUpdate.setInt(1, novaQuantidade);
                    psUpdate.setInt(2, produto.getIdProd());
                    psUpdate.executeUpdate();
                }
            } else {
                // Produto não existe, adicionar novo
                String sqlInsert = "INSERT INTO Estoque (idprod, descricao, preco, quantidade) VALUES (?, ?, ?, ?)";
                try (PreparedStatement psInsert = connection.prepareStatement(sqlInsert)) {
                    psInsert.setInt(1, produto.getIdProd());
                    psInsert.setString(2, produto.getDescricao());
                    psInsert.setDouble(3, produto.getPreco());
                    psInsert.setInt(4, quantidade);
                    psInsert.executeUpdate();
                }
            }

            carregarProdutosEstoque();  // Atualiza a tabela de estoque após a inserção/atualização
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void atualizarQuantidadeCompra(CompraProduto produto, int quantidade) {
        // Decrementar a quantidade do produto na tabela CompraProdutos
        String sqlUpdateCompra = "UPDATE CompraProdutos SET Quantidade = Quantidade - ? WHERE IdProd = ? AND IdCompra = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement psUpdateCompra = connection.prepareStatement(sqlUpdateCompra)) {
            psUpdateCompra.setInt(1, quantidade);
            psUpdateCompra.setInt(2, produto.getIdProd());
            psUpdateCompra.setInt(3, produto.getIdCompra());
            psUpdateCompra.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void editarProduto() {

    }

    @FXML
    private void removerProduto() {

    }

    private void exibirAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        // Garante que a janela de alerta fique no topo da pilha de janelas
        alert.initOwner(tabelaEstoque.getScene().getWindow());
        alert.showAndWait(); // Isso bloqueia a interação com outras janelas até o alerta ser fechado
    }


    @FXML
    private void voltar(ActionEvent event){
        carregarTela("/com/gerencia/estoque/painel-adm/painel-adm.fxml", "Voltar", event);
    }

    private void carregarTela(String caminhoFXML, String titulo, ActionEvent event) {
        try {
            // Obtem a janela atual a partir do evento, se disponível
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);

            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao carregar a tela: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
