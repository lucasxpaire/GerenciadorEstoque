package com.gerencia.estoque.controller.paineladm;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import javafx.scene.chart.LineChart;

public class VisaoGeralController {

    @FXML
    private PieChart estoquePieChart;

    @FXML
    private BarChart<String, Number> movimentacaoBarChart;

    @FXML
    private LineChart<String, Number> vendasSemanaisLineChart;

    @FXML
    private Text totalVendasText;

    @FXML
    public void initialize() {
        carregarDadosEstoque();
        carregarDadosMovimentacao();
        carregarDadosVendasSemanais();
        atualizarTotalVendas(25000.00); // Exemplo de total de vendas
    }

    private void carregarDadosEstoque() {
        estoquePieChart.getData().addAll(
                new PieChart.Data("Produto A", 30),
                new PieChart.Data("Produto B", 20),
                new PieChart.Data("Produto C", 50)

        );
    }

    private void carregarDadosMovimentacao() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Movimentação");

        // Adiciona cada produto com uma cor específica
        XYChart.Data<String, Number> produtoA = new XYChart.Data<>("Produto A", 200);
        XYChart.Data<String, Number> produtoB = new XYChart.Data<>("Produto B", 150);
        XYChart.Data<String, Number> produtoC = new XYChart.Data<>("Produto C", 300);

        // Define cores específicas para cada barra
        produtoA.nodeProperty().addListener((obs, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: #E46434;")); // Laranja
        produtoB.nodeProperty().addListener((obs, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: #FB8C00;")); // Laranja
        produtoC.nodeProperty().addListener((obs, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: #43A047;")); // Verde

        // Adiciona os dados ao gráfico
        series.getData().addAll(produtoA, produtoB, produtoC);
        movimentacaoBarChart.getData().add(series);
    }


    private void carregarDadosVendasSemanais() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Produto Mais Vendido");

        series.getData().add(new XYChart.Data<>("Segunda", 50));
        series.getData().add(new XYChart.Data<>("Terça", 60));
        series.getData().add(new XYChart.Data<>("Quarta", 70));
        series.getData().add(new XYChart.Data<>("Quinta", 65));
        series.getData().add(new XYChart.Data<>("Sexta", 80));
        series.getData().add(new XYChart.Data<>("Sábado", 120));
        series.getData().add(new XYChart.Data<>("Domingo", 90));

        vendasSemanaisLineChart.getData().add(series);
    }

    private void atualizarTotalVendas(double total) {
        totalVendasText.setText(String.format("Total de Vendas: R$ %.2f", total));
    }

    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-prop/painel-prop.fxml", "Painel de Controle do Proprietário", event);
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
