package com.gerencia.estoque.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.chart.XYChart;

public class VisaoGeralController {

    @FXML
    private PieChart pieChartEstoque;

    @FXML
    private BarChart<String, Number> barChartVendas;

    @FXML
    private TableView<?> tabelaProdutos;

    @FXML
    private TableColumn<?, ?> colProduto;

    @FXML
    private TableColumn<?, ?> colVendas;

    @FXML
    private TableColumn<?, ?> colEstoque;

    @FXML
    private Label labelTotalVendas;

    @FXML
    private Label labelTotalCompras;

    @FXML
    public void initialize() {
        // Exemplo de preenchimento do gráfico de pizza
        pieChartEstoque.getData().addAll(
                new PieChart.Data("Produto A", 20),
                new PieChart.Data("Produto B", 50),
                new PieChart.Data("Produto C", 30)
        );

        // Exemplo de preenchimento do gráfico de barras
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Produto A", 100));
        series.getData().add(new XYChart.Data<>("Produto B", 200));
        series.getData().add(new XYChart.Data<>("Produto C", 150));
        barChartVendas.getData().add(series);

        // Exemplo de resumo financeiro
        labelTotalVendas.setText("Total de Vendas: R$ 5.000,00");
        labelTotalCompras.setText("Total de Compras: R$ 3.000,00");
    }

}
