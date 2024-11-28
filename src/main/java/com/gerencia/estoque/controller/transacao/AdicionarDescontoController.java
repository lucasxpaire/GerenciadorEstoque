package com.gerencia.estoque.controller.transacao;

import com.gerencia.estoque.dao.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdicionarDescontoController {

    @FXML
    private ComboBox<String> cbTipoDesconto;
    @FXML
    private TextField tfPercentual;
    @FXML
    private TextField tfDescricao;

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        // Obtém a janela principal para centralizar o alerta
        Stage stage = (Stage) tfPercentual.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        // Maximiza a janela principal para garantir que o alerta esteja visível
        //stage.setMaximized(true);

        // Exibe o alerta
        alert.showAndWait();
    }

    // Método para salvar desconto
    @FXML
    private void handleSalvarDesconto() {
        String tipoDesconto = cbTipoDesconto.getValue();
        double percentual;
        String descricao = tfDescricao.getText();

        try {
            percentual = Double.parseDouble(tfPercentual.getText());
            if (percentual <= 0 || percentual > 100) {
                mostrarAlerta("Erro", "Percentual de desconto inválido. Deve ser entre 0 e 100.", Alert.AlertType.ERROR);
                return;
            }

            // Salvar o desconto no banco de dados
            try (Connection connection = Database.getConnection()) {
                String query = "INSERT INTO Desconto (Tipo, Percentual, Descricao) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, tipoDesconto);
                    statement.setDouble(2, percentual);
                    statement.setString(3, descricao);
                    statement.executeUpdate();
                }

                mostrarAlerta("Sucesso", "Desconto criado com sucesso!", Alert.AlertType.INFORMATION);
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Percentual de desconto deve ser um número válido.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao salvar o desconto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
