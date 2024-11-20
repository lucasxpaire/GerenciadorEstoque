package com.gerencia.estoque.controller.vender;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class VenderController {

    @FXML
    private TextField campoNomeProduto;
    @FXML
    private TextField campoSkuProduto;
    @FXML
    private TextField campoPrecoProduto;
    @FXML
    private TextField campoQuantidade;

    // Método para abrir a seleção de produtos
    @FXML
    public void abrirSelecaoProduto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gerencia/estoque/selecionar-produto.fxml"));
            Parent root = loader.load();

            // Obtém a controladora da seleção de produtos e define o callback
            SelecionarProdutoController selecionarProdutoController = loader.getController();
            selecionarProdutoController.setVenderController(this);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Selecionar Produto");
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Recebe os dados do produto selecionado
    public void setProdutoSelecionado(String nome, String sku, double preco) {
        campoNomeProduto.setText(nome);
        campoSkuProduto.setText(sku);
        campoPrecoProduto.setText(String.valueOf(preco));
    }

    // Placeholder para finalizar a venda
    @FXML
    public void finalizarVenda(ActionEvent event) {
        // Lógica para finalizar a venda, incluindo a quantidade e o preço
    }
    @FXML
    public void aplicarDesconto(ActionEvent event){

    }
    @FXML
    public void verificarFidelidade(ActionEvent event){

    }

    // Placeholder para voltar ao painel
    @FXML
    public void voltar(ActionEvent event) {
        // Lógica para voltar ao painel
    }
}
