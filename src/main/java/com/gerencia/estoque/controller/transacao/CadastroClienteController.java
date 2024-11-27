package com.gerencia.estoque.controller.transacao;

import com.gerencia.estoque.dao.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroClienteController {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCpf;

    // Método para exibir alertas consistentes com o TransacaoController
    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        // Obtém a janela principal para centralizar o alerta
        Stage stage = (Stage) tfNome.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    @FXML
    private void handleSalvar() {
        String nome = tfNome.getText();
        String cpf = tfCpf.getText();

        if (nome.isEmpty() || cpf.isEmpty()) {
            mostrarAlerta("Erro", "Todos os campos são obrigatórios.", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connection = Database.getConnection()) {
            // Primeiro insere o cliente na tabela Cliente
            String sqlCliente = "INSERT INTO Cliente (Nome, CPF) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sqlCliente)) {
                statement.setString(1, nome);
                statement.setString(2, cpf);
                statement.executeUpdate();
            }

            // Agora, insere o cliente na tabela Fidelidade com 0 pontos
            String sqlFidelidade = "INSERT INTO Fidelidade (IdCliente, Pontos) VALUES ((SELECT IdCliente FROM Cliente WHERE CPF = ?), 0)";
            try (PreparedStatement statementFidelidade = connection.prepareStatement(sqlFidelidade)) {
                statementFidelidade.setString(1, cpf);
                statementFidelidade.executeUpdate();
            }

            mostrarAlerta("Sucesso", "Cliente cadastrado com sucesso!", Alert.AlertType.INFORMATION);

            // Fecha a janela após o sucesso
            Stage stage = (Stage) tfNome.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Erro ao cadastrar cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
