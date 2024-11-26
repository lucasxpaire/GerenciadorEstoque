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

    @FXML
    private void handleSalvar() {
        String nome = tfNome.getText();
        String cpf = tfCpf.getText();

        if (nome.isEmpty() || cpf.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Todos os campos são obrigatórios.");
            alert.show();
            return;
        }

        try (Connection connection = Database.getConnection()) {
            String sql = "INSERT INTO Cliente (Nome, CPF) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nome);
                statement.setString(2, cpf);
                statement.executeUpdate();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cliente cadastrado com sucesso!");
            alert.show();

            // Fecha a janela após o sucesso
            Stage stage = (Stage) tfNome.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar cliente.");
            alert.show();
        }
    }
}
