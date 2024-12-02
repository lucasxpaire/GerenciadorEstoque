package com.gerencia.estoque.controller.estoque;

import com.gerencia.estoque.dao.EstoqueDAO;
import com.gerencia.estoque.model.estoque.Estoque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class ComprarMaisQuantidadeController {

    @FXML
    private ComboBox<String> comboProduto;
    @FXML
    private TextField quantidadeField;

    @FXML
    public void initialize() {
        try {
            EstoqueDAO estoqueDAO = new EstoqueDAO();
            List<Estoque> estoqueList = estoqueDAO.buscarEstoque();

            ObservableList<String> produtos = FXCollections.observableArrayList();

            for (Estoque estoque : estoqueList) {
                produtos.add(estoque.getDescricao());
            }

            comboProduto.setItems(produtos);

        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao carregar os produtos do estoque.");
            e.printStackTrace();
        }
    }

    @FXML
    private void confirmarCompra() {
        String produtoSelecionado = comboProduto.getValue();
        String quantidadeTexto = quantidadeField.getText();

        if (produtoSelecionado == null || quantidadeTexto.isEmpty()) {
            mostrarAlerta("Erro", "Selecione um produto e insira a quantidade.");
            return;
        }

        try {
            int quantidade = Integer.parseInt(quantidadeTexto);
            if (quantidade <= 0) {
                mostrarAlerta("Erro", "A quantidade deve ser um número positivo.");
                return;
            }

            EstoqueDAO estoqueDAO = new EstoqueDAO();
            int idProduto = estoqueDAO.buscarIdProduto(produtoSelecionado);

            if (idProduto != -1) {
                estoqueDAO.atualizarEstoque(idProduto, quantidade);
                mostrarAlerta("Sucesso", "Estoque atualizado com sucesso!", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Erro", "Produto não encontrado.");
            }

        } catch (SQLException e) {
            mostrarAlerta("Erro", "Erro ao acessar o banco de dados. Tente novamente mais tarde.");
            e.printStackTrace();
        }

        fecharJanela();
    }

    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) comboProduto.getScene().getWindow();
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
        Stage stage = (Stage) quantidadeField.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
