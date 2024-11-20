package com.gerencia.estoque.controller.vender;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class SelecionarProdutoController {

    @FXML
    private ListView<String> listaProdutos;

    private VenderController venderController;

    // Define o controlador de venda principal
    public void setVenderController(VenderController venderController) {
        this.venderController = venderController;
        carregarProdutos(); // Carrega os produtos ao abrir a janela
    }

    // Método para carregar os produtos na lista (exemplo estático)
    private void carregarProdutos() {
        listaProdutos.getItems().addAll(
                "Produto A - SKU123 - R$10.00",
                "Produto B - SKU456 - R$20.00",
                "Produto C - SKU789 - R$30.00"
        );
    }

    // Ao selecionar o produto, envia os dados ao VenderController
    @FXML
    public void selecionarProduto() {
        String produtoSelecionado = listaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            String[] dadosProduto = produtoSelecionado.split(" - ");
            String nome = dadosProduto[0];
            String sku = dadosProduto[1];
            double preco = Double.parseDouble(dadosProduto[2].replace("R$", "").trim());

            venderController.setProdutoSelecionado(nome, sku, preco);
            Stage stage = (Stage) listaProdutos.getScene().getWindow();
            stage.close();
        }
    }
}
