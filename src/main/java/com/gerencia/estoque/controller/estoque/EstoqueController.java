package com.gerencia.estoque.controller.estoque;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class EstoqueController {

    @FXML
    private Pane estoquePane;

    @FXML
    private void abrirComprarProduto() {
        abrirJanela("/com/gerencia/estoque/estoque/comprar_produto.fxml", "Comprar Produto");
    }

    @FXML
    private void abrirEstoque(){
        abrirJanela("/com/gerencia/estoque/estoque/gerenciar_estoque.fxml", "Gerenciar Estoque");
    }

    @FXML
    private void voltar(ActionEvent event){
        carregarTela("/com/gerencia/estoque/painel-adm/painel-adm.fxml", "Voltar", event);
    }

    private void abrirJanela(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            //criando um novo stage (janela)

            Stage stage = new Stage();
            stage.setTitle(titulo);
            //stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initModality(Modality.NONE);

            if(estoquePane != null && estoquePane.getScene() != null){
                stage.initOwner(estoquePane.getScene().getWindow());
            }

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
