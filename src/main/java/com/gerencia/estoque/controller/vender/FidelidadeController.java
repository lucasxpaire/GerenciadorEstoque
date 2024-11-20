package com.gerencia.estoque.controller.vender;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

//public class FidelidadeController {
//
//    @FXML
//    private Label labelSaldoPontos;
//    @FXML
//    private TableView<HistoricoFidelidade> tableHistorico;
//    @FXML
//    private TableColumn<HistoricoFidelidade, String> colunaDataTransacao;
//    @FXML
//    private TableColumn<HistoricoFidelidade, Double> colunaValorCompra;
//    @FXML
//    private TableColumn<HistoricoFidelidade, Integer> colunaPontosAcumulados;
//
//    private Cliente cliente;
//
//    public void initialize() {
//        // Inicializa os dados de fidelidade do cliente
//        colunaDataTransacao.setCellValueFactory(new PropertyValueFactory<>("data"));
//        colunaValorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
//        colunaPontosAcumulados.setCellValueFactory(new PropertyValueFactory<>("pontosAcumulados"));
//    }
//
//    @FXML
//    private void resgatarPontos() {
//        // Lógica para resgatar pontos e aplicar desconto
//        int pontos = cliente.getPontosAcumulados();
//        if (pontos >= 100) {
//            // Resgata 100 pontos para aplicar um desconto
//            double desconto = 10.0;  // Exemplo de desconto
//            // Aplica o desconto na venda
//            mostrarAlerta("Resgatar Pontos", "Desconto de " + desconto + " aplicado!");
//        } else {
//            mostrarAlerta("Resgatar Pontos", "Saldo de pontos insuficiente.");
//        }
//    }
//
//    private void mostrarAlerta(String titulo, String mensagem) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(titulo);
//        alert.setHeaderText(null);
//        alert.setContentText(mensagem);
//        alert.showAndWait();
//    }
//}
