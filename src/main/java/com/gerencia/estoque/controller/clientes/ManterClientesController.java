package com.gerencia.estoque.controller.clientes;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.clientes.Cliente;
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

public class ManterClientesController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoCPF;

    @FXML
    private TableView<Cliente> tableClientes;

    @FXML
    private TableColumn<Cliente, String> colunaNome;

    @FXML
    private TableColumn<Cliente, String> colunaCPF;

    private ObservableList<Cliente> listaClientes;

    @FXML
    public void initialize() {
        listaClientes = FXCollections.observableArrayList();

        colunaNome.setCellValueFactory(data -> data.getValue().nomeProperty());
        colunaNome.setStyle("-fx-alignment: CENTER;");
        colunaCPF.setCellValueFactory(data -> data.getValue().cpfProperty());
        colunaCPF.setStyle("-fx-alignment: CENTER;");

        tableClientes.setItems(listaClientes);

        carregarClientes();

        tableClientes.setOnMouseClicked(event -> {
            Cliente clienteSelecionado = tableClientes.getSelectionModel().getSelectedItem();
            if (clienteSelecionado != null) {
                campoNome.setText(clienteSelecionado.getNome());
                campoCPF.setText(clienteSelecionado.getCpf());
            }
        });
    }

    private void carregarClientes() {
        listaClientes.clear();
        String sql = "SELECT IdCliente, Nome, CPF FROM Cliente";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("IdCliente"),
                        rs.getString("Nome"),
                        rs.getString("CPF")
                );
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao carregar clientes: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void adicionarCliente() {
        String nome = campoNome.getText();
        String cpf = campoCPF.getText();

        if (nome.isEmpty() || cpf.isEmpty()) {
            mostrarAlerta("Erro", "Todos os campos devem ser preenchidos.", Alert.AlertType.WARNING);
            return;
        }

        String sql = "INSERT INTO Cliente (Nome, CPF) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.executeUpdate();

            mostrarAlerta("Sucesso", "Cliente adicionado com sucesso.", Alert.AlertType.INFORMATION);
            carregarClientes();
            limparCampos();
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao adicionar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void editarCliente() {
        Cliente clienteSelecionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cliente para editar.", Alert.AlertType.WARNING);
            return;
        }

        String nome = campoNome.getText();
        String cpf = campoCPF.getText();

        String sql = "UPDATE Cliente SET Nome = ?, CPF = ? WHERE IdCliente = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setInt(3, clienteSelecionado.getIdCliente());
            stmt.executeUpdate();

            mostrarAlerta("Sucesso", "Cliente editado com sucesso.", Alert.AlertType.INFORMATION);
            carregarClientes();
            limparCampos();
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao editar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void removerCliente() {
        Cliente clienteSelecionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cliente para remover.", Alert.AlertType.WARNING);
            return;
        }

        String sql = "DELETE FROM Cliente WHERE IdCliente = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteSelecionado.getIdCliente());
            stmt.executeUpdate();

            mostrarAlerta("Sucesso", "Cliente removido com sucesso.", Alert.AlertType.INFORMATION);
            carregarClientes();
            limparCampos();
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao remover cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limparCampos() {
        campoNome.clear();
        campoCPF.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        // Centraliza o alerta em relação à janela principal
        Stage stage = (Stage) campoNome.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-prop/painel-prop.fxml", "Painel do Administrador", event);
    }

    private void carregarTela(String caminhoFXML, String titulo, ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao carregar a tela: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
