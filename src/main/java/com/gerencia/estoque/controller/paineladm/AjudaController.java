package com.gerencia.estoque.controller.paineladm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AjudaController {

    @FXML
    private ListView<String> listaTopicos;
    @FXML
    private Label tituloAjuda;
    @FXML
    private TextArea conteudoAjuda;

    @FXML
    public void initialize() {
        listaTopicos.getItems().addAll(
                "Como realizar uma venda",
                "Como adicionar um produto",
                "Como visualizar o estoque",
                "Como gerar relatórios"
        );
    }

    @FXML
    public void exibirConteudoAjuda(MouseEvent event) {
        String topicoSelecionado = listaTopicos.getSelectionModel().getSelectedItem();

        if (topicoSelecionado != null) {
            tituloAjuda.setText(topicoSelecionado);

            switch (topicoSelecionado) {
                case "Como realizar uma venda":
                    conteudoAjuda.setText("Para realizar uma venda, siga os seguintes passos...\n1. Selecione o produto...\n2. Escolha o cliente...");
                    break;
                case "Como adicionar um produto":
                    conteudoAjuda.setText("Para adicionar um produto, vá até o menu de estoque e clique em 'Adicionar Produto'...");
                    break;
                case "Como visualizar o estoque":
                    conteudoAjuda.setText("Para visualizar o estoque, acesse o menu 'Estoque' e selecione a opção 'Ver Estoque'...");
                    break;
                case "Como gerar relatórios":
                    conteudoAjuda.setText("Para gerar relatórios, vá até o menu 'Relatórios' e escolha o tipo de relatório desejado...");
                    break;
                default:
                    conteudoAjuda.setText("Selecione um tópico para visualizar a ajuda.");
            }
        }
    }

    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-prop/painel-prop.fxml", "Login", event);
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
