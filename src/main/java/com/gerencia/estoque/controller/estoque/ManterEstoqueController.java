package com.gerencia.estoque.controller.estoque;

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
    public void goToComprarItem(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/estoque/comprar-item.fxml", "Comprar Item", event);
    }

    @FXML
    public void goToAlterarItem(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/estoque/alterar-item.fxml", "Alterar Item", event);
    }

    @FXML
    public void goToAdicionarItemEstoque(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/estoque/adicionar-item-estoque.fxml", "Adicionar Item ao Estoque", event);
    }

    @FXML
    public void goToRemoverItemEstoque(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/estoque/remover-item-estoque.fxml", "Remover Item do Estoque", event);
    }

    public void gotToVisualizarEstoque(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/estoque/visualizar-estoque.fxml", "Visualizar itens do Estoque", event);
    }

    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-adm/painel-adm.fxml", "Painel do Proprietário", event);
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
