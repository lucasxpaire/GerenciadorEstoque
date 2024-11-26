package com.gerencia.estoque.controller.transacao;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.transacao.Cliente;
import com.gerencia.estoque.model.transacao.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransacaoController {

    @FXML
    private ComboBox<Produto> cbProduto;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private ComboBox<Cliente> cbCliente;

    @FXML
    private TableView<String> tvResumo;

    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarProdutos();
        carregarClientes();
    }

    private void carregarProdutos() {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT IdProduto, Descricao, Preco FROM Produto";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    listaProdutos.add(new Produto(
                            rs.getInt("IdProduto"),
                            rs.getString("Descricao"),
                            rs.getDouble("Preco")
                    ));
                }
                cbProduto.setItems(listaProdutos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarClientes() {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT IdCliente, Nome, CPF FROM Cliente";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    listaClientes.add(new Cliente(
                            rs.getInt("IdCliente"),
                            rs.getString("Nome"),
                            rs.getString("CPF")
                    ));
                }
                cbCliente.setItems(listaClientes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCadastroCliente() {
        // Exemplo simplificado para cadastro de cliente
        String nome = "Novo Cliente"; // Pode ser capturado de um campo de texto
        String cpf = "000.000.000-00"; // Pode ser capturado de um campo de texto

        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO Cliente (Nome, CPF) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, nome);
                statement.setString(2, cpf);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        carregarClientes(); // Atualizar a lista de clientes
    }

    @FXML
    private void handleConfirmarTransacao() {
        Produto produtoSelecionado = cbProduto.getValue();
        Cliente clienteSelecionado = cbCliente.getValue();
        int quantidade = Integer.parseInt(tfQuantidade.getText());

        if (produtoSelecionado == null || quantidade <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Selecione um produto e informe uma quantidade válida.");
            alert.show();
            return;
        }

        try (Connection connection = Database.getConnection()) {
            // Adicionar transação
            String query = "INSERT INTO Transacao (IdProduto, IdFuncionario, IdCliente, Preco, Descricao, Quantidade, Tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, produtoSelecionado.getIdProduto());
                statement.setInt(2, 1); // Exemplo: IdFuncionario pode ser recuperado do login
                statement.setInt(3, clienteSelecionado != null ? clienteSelecionado.getIdCliente() : null);
                statement.setDouble(4, produtoSelecionado.getPreco() * quantidade);
                statement.setString(5, produtoSelecionado.getDescricao());
                statement.setInt(6, quantidade);
                statement.setString(7, "saida");
                statement.executeUpdate();
            }

            // Atualizar estoque
            String updateEstoque = "UPDATE Estoque SET Quantidade = Quantidade - ? WHERE IdProduto = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateEstoque)) {
                statement.setInt(1, quantidade);
                statement.setInt(2, produtoSelecionado.getIdProduto());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Venda concluída com sucesso!");
        alert.show();
    }

    @FXML
    private void handleAbrirCadastroCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gerencia/estoque/transacao/cadastro_cliente.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Cliente");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Aguarda a janela ser fechada antes de continuar

            carregarClientes(); // Atualiza a lista de clientes
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao abrir a janela de cadastro.");
            alert.show();
        }
    }
    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-prop/painel-prop.fxml", "Painel do Proprietário", event);
    }

    // Método para carregar a tela específica
    private void carregarTela(String caminhoFXML, String titulo, ActionEvent event) {
        try {
            // Obtem a janela atual a partir do evento, se disponível
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao carregar a tela: " + e.getMessage());
        }
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
