package com.gerencia.estoque.controller.estoque;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.estoque.Estoque;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoverItemController {

    @FXML
    private Label labelConfirmacao;

    @FXML
    private TextField quantidadeField;

    private Estoque itemEstoque;

    public void setItemEstoque(Estoque itemEstoque) {
        this.itemEstoque = itemEstoque;
        labelConfirmacao.setText("Quantidade atual: " + itemEstoque.getQuantidade());
    }

    @FXML
    private void removerItem() {
        if (quantidadeField.getText().isEmpty()) {
            mostrarAlerta("Erro", "Por favor, insira uma quantidade.");
            return;
        }

        int quantidadeRemover;

        try {
            quantidadeRemover = Integer.parseInt(quantidadeField.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "A quantidade deve ser um número válido.");
            return;
        }

        if (quantidadeRemover <= 0 || quantidadeRemover > itemEstoque.getQuantidade()) {
            mostrarAlerta("Erro", "A quantidade deve ser positiva e menor ou igual à quantidade disponível.");
            return;
        }

        String sql = "UPDATE Estoque SET Quantidade = Quantidade - ? WHERE IdProduto = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantidadeRemover);
            ps.setInt(2, itemEstoque.getIdProduto());
            ps.executeUpdate();

            mostrarAlerta("Sucesso", "Quantidade removida com sucesso.");
            fecharJanela();

        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao remover quantidade: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelarRemocao() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) quantidadeField.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        // Obtendo a janela principal para configurar o alerta acima dela
        Stage stage = (Stage) quantidadeField.getScene().getWindow();
        alert.initOwner(stage);  // Define o dono da janela do alerta
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);  // Torna o alerta modal (na frente da janela principal)

        alert.showAndWait();  // Exibe o alerta
    }
}
