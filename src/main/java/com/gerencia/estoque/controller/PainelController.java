package com.gerencia.estoque.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.EventObject;

public class PainelController {

    @FXML
    public void goToVisaoGeral(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/visao-geral.fxml", "Visão Geral do Dono", event);
    }

    @FXML
    public void goToRelatorios(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/relatorios.fxml", "Relatórios Detalhados", event);
    }

    @FXML
    public void goToManterFuncionarios(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/manter-funcionarios.fxml", "Configuração de Funcionários", event);
    }

    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/login.fxml", "Login", event);
    }

    @FXML
    public void goToManterEstoque(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/manter-estoque.fxml", "Manter Estoque", event);
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
