package com.gerencia.estoque.controller.vender;

//public class DescontoController {
//
//    @FXML
//    private TextField campoDesconto;
//    @FXML
//    private Label labelPrecoOriginal;
//    @FXML
//    private Label labelPrecoComDesconto;
//
//    private Produto produto;
//
//    public void initialize() {
//        // Inicializa a tela com o preço original do produto
//        labelPrecoOriginal.setText("Preço Original: " + produto.getPreco());
//    }
//
//    @FXML
//    private void aplicarDesconto() {
//        // Calcula o desconto baseado no percentual inserido
//        double descontoPercentual = Double.parseDouble(campoDesconto.getText());
//        double precoOriginal = produto.getPreco();
//        double valorDesconto = precoOriginal * (descontoPercentual / 100);
//        double precoComDesconto = precoOriginal - valorDesconto;
//
//        labelPrecoComDesconto.setText("Preço com Desconto: " + precoComDesconto);
//        produto.setPreco(precoComDesconto);  // Atualiza o preço do produto com desconto
//    }
//
//    @FXML
//    private void voltar() {
//        // Retorna à tela anterior
//    }
//}
