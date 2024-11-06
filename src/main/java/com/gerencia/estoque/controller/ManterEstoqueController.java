package com.gerencia.estoque.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ManterEstoqueController {

    @FXML
    public void goToCadastrarProduto(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/cadastro-produtos.fxml", "Cadastrar Produto", event);
    }

    @FXML
    public void goToEditarProduto(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/editar-produtos.fxml", "Editar Produto", event);
    }

    @FXML
    public void goToRemoverProduto(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/remover-produtos.fxml", "Remover Produto", event);
    }

    @FXML
    public void goToVisualizarEstoque(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/visualizar-estoque.fxml", "Visualizar Estoque", event);
    }

    @FXML
    public void voltarPainelAdm(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-adm.fxml", "Painel do Administrador", event);
    }

    private void carregarTela(String caminhoFXML, String titulo, ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Falha ao carregar a tela: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
