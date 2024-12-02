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

    // Definição dos componentes FXML
    @FXML
    private ListView<String> listaTopicos; // Lista de tópicos de ajuda

    @FXML
    private Label tituloAjuda; // Título da seção de ajuda

    @FXML
    private TextArea conteudoAjuda; // Área de conteúdo da ajuda

    @FXML
    private Button voltarButton; // Botão de voltar

    // Método que inicializa a tela e carrega os tópicos de ajuda
    @FXML
    public void initialize() {
        // Exemplo de tópicos de ajuda que podem ser dinamicamente carregados
        listaTopicos.getItems().addAll(
                "Como realizar uma venda",
                "Como adicionar um produto",
                "Como visualizar o estoque",
                "Como gerar relatórios"
        );
    }

    // Método que é chamado quando um tópico é clicado na lista
    @FXML
    public void exibirConteudoAjuda(MouseEvent event) {
        String topicoSelecionado = listaTopicos.getSelectionModel().getSelectedItem();

        if (topicoSelecionado != null) {
            // Alterando o título com base no tópico selecionado
            tituloAjuda.setText(topicoSelecionado);

            // Definindo o conteúdo do tópico
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

    // Método de navegação para voltar à tela anterior
    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/login.fxml", "Login", event);
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
