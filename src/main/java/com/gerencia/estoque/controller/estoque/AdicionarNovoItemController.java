package com.gerencia.estoque.controller.estoque;

import com.gerencia.estoque.dao.EstoqueDAO;

import com.gerencia.estoque.model.estoque.Estoque;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AdicionarNovoItemController {

    @FXML
    private TextField descricaoField;
    @FXML
    private TextField precoField;
    @FXML
    private TextField quantidadeField;

    @FXML
    private void adicionarProduto() {
        String descricao = descricaoField.getText();
        String precoTexto = precoField.getText();
        String quantidadeTexto = quantidadeField.getText();

        if (descricao.isEmpty() || precoTexto.isEmpty() || quantidadeTexto.isEmpty()) {
            mostrarAlerta("Erro", "Por favor, preencha todos os campos.");
            return;
        }

        try {
            double preco = Double.parseDouble(precoTexto);
            int quantidade = Integer.parseInt(quantidadeTexto);

            if (preco < 0 || quantidade < 0) {
                mostrarAlerta("Erro", "Preço e quantidade devem ser números positivos.");
                return;
            }

            Estoque novoProduto = new Estoque(0, preco, descricao, quantidade); // 0 para idProduto

            EstoqueDAO estoqueDAO = new EstoqueDAO();
            estoqueDAO.adicionarProduto(novoProduto);


            mostrarAlerta("Sucesso", "Produto adicionado ao estoque!", Alert.AlertType.INFORMATION);
            descricaoField.clear();
            precoField.clear();
            quantidadeField.clear();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Preço e quantidade devem ser números válidos.");
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao acessar o banco de dados. Tente novamente mais tarde.");
        }
    }

    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) descricaoField.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        mostrarAlerta(titulo, mensagem, Alert.AlertType.ERROR);
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        Stage stage = (Stage) descricaoField.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

}
