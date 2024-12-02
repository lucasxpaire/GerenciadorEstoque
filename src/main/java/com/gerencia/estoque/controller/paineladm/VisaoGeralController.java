package com.gerencia.estoque.controller.paineladm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.*;
import com.gerencia.estoque.dao.Database;
import javafx.stage.Stage;

public class VisaoGeralController {

    @FXML
    private PieChart estoquePieChart;
    @FXML
    private BarChart<String, Number> movimentacaoBarChart;
    @FXML
    private LineChart<String, Number> vendasSemanaisLineChart;
    @FXML
    private Label totalVendasText;

    private Connection connection;

    public void initialize() {
        try {
            connection = Database.getConnection();
            carregarEstoque();
            carregarMovimentacao();
            carregarVendasSemanais();
            carregarTotalVendas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarEstoque() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Query para obter os dados de estoque
        String sql = "SELECT e.Descricao, e.Quantidade FROM Estoque e";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String descricao = rs.getString("Descricao");
                int quantidade = rs.getInt("Quantidade");
                pieChartData.add(new PieChart.Data(descricao, quantidade));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        estoquePieChart.setData(pieChartData);
    }

    private void carregarMovimentacao() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Produtos com Maior Movimentação");

        // Query para obter a movimentação dos produtos
        String sql = "SELECT p.Descricao, SUM(t.Quantidade) as QuantidadeMovimentada " +
                "FROM Transacao t " +
                "JOIN Produto p ON t.IdProduto = p.IdProduto " +
                "GROUP BY p.Descricao " +
                "ORDER BY QuantidadeMovimentada DESC LIMIT 10"; // Top 10 produtos

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String descricao = rs.getString("Descricao");
                int quantidadeMovimentada = rs.getInt("QuantidadeMovimentada");
                series.getData().add(new XYChart.Data<>(descricao, quantidadeMovimentada));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        movimentacaoBarChart.getData().clear();
        movimentacaoBarChart.getData().add(series);
    }

    private void carregarVendasSemanais() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Vendas Semanais");

        // Obter o ID do produto mais vendido
        String produtoMaisVendidoSql = "SELECT t.IdProduto, SUM(t.Quantidade) as TotalVendas " +
                "FROM Transacao t " +
                "GROUP BY t.IdProduto " +
                "ORDER BY TotalVendas DESC LIMIT 1";

        int idProdutoMaisVendido = 0;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(produtoMaisVendidoSql)) {
            if (rs.next()) {
                idProdutoMaisVendido = rs.getInt("IdProduto");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Obter as vendas semanais do produto mais vendido
        if (idProdutoMaisVendido > 0) {
            String vendasSemanaisSql = "SELECT EXTRACT(DOW FROM t.DataHora) AS DiaSemana, SUM(t.Quantidade) AS QuantidadeVendida " +
                    "FROM Transacao t " +
                    "WHERE t.IdProduto = ? " +
                    "GROUP BY DiaSemana ORDER BY DiaSemana";

            try (PreparedStatement stmt = connection.prepareStatement(vendasSemanaisSql)) {
                stmt.setInt(1, idProdutoMaisVendido);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String diaSemana = getDiaDaSemana(rs.getInt("DiaSemana"));
                        int quantidadeVendida = rs.getInt("QuantidadeVendida");
                        series.getData().add(new XYChart.Data<>(diaSemana, quantidadeVendida));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        vendasSemanaisLineChart.getData().clear();
        vendasSemanaisLineChart.getData().add(series);
    }

    private void carregarTotalVendas() {
        // Query para obter o total de vendas
        String sql = "SELECT SUM(t.Preco * t.Quantidade) AS TotalVendas " +
                "FROM Transacao t";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                double totalVendas = rs.getDouble("TotalVendas");
                totalVendasText.setText("Total de Vendas: R$ " + String.format("%.2f", totalVendas));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getDiaDaSemana(int index) {
        String[] dias = {"Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"};
        return dias[index];
    }

    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-prop/painel-prop.fxml", "Login", event);
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

    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
