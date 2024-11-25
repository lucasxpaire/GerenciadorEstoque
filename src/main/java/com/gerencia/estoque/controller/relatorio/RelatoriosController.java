package com.gerencia.estoque.controller.relatorio;

import com.gerencia.estoque.model.relatorio.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class RelatoriosController {

    // Tabelas
    @FXML private TableView<Venda> tabelaVendas;
    @FXML private TableView<Compra> tabelaCompras;
    @FXML private TableView<Estoque> tabelaEstoque;
    @FXML private TableView<Movimentacao> tabelaMovimentacoes;
    @FXML private TableView<Produto> tabelaMaisVendidos;
    @FXML private TableView<Produto> tabelaMenosMovimentados;

    // Colunas de Vendas
    @FXML private TableColumn<Venda, String> colunaDataVenda;
    @FXML private TableColumn<Venda, String> colunaProdutoVenda;
    @FXML private TableColumn<Venda, Integer> colunaQuantidadeVenda;
    @FXML private TableColumn<Venda, Double> colunaTotalVenda;

    // Colunas de Compras
    @FXML private TableColumn<Compra, String> colunaDataCompra;
    @FXML private TableColumn<Compra, String> colunaProdutoCompra;
    @FXML private TableColumn<Compra, Integer> colunaQuantidadeCompra;
    @FXML private TableColumn<Compra, Double> colunaTotalCompra;

    // Colunas de Estoque
    @FXML private TableColumn<Estoque, String> colunaProdutoEstoque;
    @FXML private TableColumn<Estoque, Integer> colunaQuantidadeEstoque;
    @FXML private TableColumn<Estoque, Double> colunaValorEstoque;

    // Colunas de Movimentação
    @FXML private TableColumn<Movimentacao, String> colunaDataMovimento;
    @FXML private TableColumn<Movimentacao, String> colunaProdutoMovimento;
    @FXML private TableColumn<Movimentacao, Integer> colunaQuantidadeMovimento;
    @FXML private TableColumn<Movimentacao, String> colunaTipoMovimento;

    // Colunas de Produtos Mais Vendidos
    @FXML private TableColumn<Produto, String> colunaProdutoMaisVendido;
    @FXML private TableColumn<Produto, Integer> colunaQuantidadeVendida;

    // Colunas de Produtos Menos Movimentados
    @FXML private TableColumn<Produto, String> colunaProdutoMenosMovimentado;
    @FXML private TableColumn<Produto, Integer> colunaQuantidadeMovimentada;

    // Botões
    @FXML private Button btnGerarVendasPDF;
    @FXML private Button btnGerarComprasPDF;
    @FXML private Button btnGerarEstoquePDF;
    @FXML private Button btnGerarHistoricoPDF;
    @FXML private Button btnGerarMaisVendidosPDF;
    @FXML private Button btnGerarMenosMovimentadosPDF;

    @FXML
    private void initialize() {
        // Inicialização das tabelas (listas fictícias)
        ObservableList<Venda> vendas = FXCollections.observableArrayList();
        tabelaVendas.setItems(vendas);

        ObservableList<Compra> compras = FXCollections.observableArrayList();
        tabelaCompras.setItems(compras);

        ObservableList<Estoque> estoques = FXCollections.observableArrayList();
        tabelaEstoque.setItems(estoques);

        ObservableList<Movimentacao> movimentacoes = FXCollections.observableArrayList();
        tabelaMovimentacoes.setItems(movimentacoes);

        ObservableList<Produto> maisVendidos = FXCollections.observableArrayList();
        tabelaMaisVendidos.setItems(maisVendidos);

        ObservableList<Produto> menosMovimentados = FXCollections.observableArrayList();
        tabelaMenosMovimentados.setItems(menosMovimentados);
    }

    // Métodos para gerar relatórios
    @FXML
    private void gerarRelatorioVendasDiarias() {
        // Placeholder: Lógica para gerar relatório de vendas diário
        showAlert("Gerar Relatório de Vendas", "Relatório de vendas diárias gerado!");
    }

    @FXML
    private void gerarRelatorioVendasSemanais() {
        // Placeholder: Lógica para gerar relatório de vendas semanal
        showAlert("Gerar Relatório de Vendas", "Relatório de vendas semanais gerado!");
    }

    @FXML
    private void gerarRelatorioVendasMensais() {
        // Placeholder: Lógica para gerar relatório de vendas mensal
        showAlert("Gerar Relatório de Vendas", "Relatório de vendas mensais gerado!");
    }

    // Métodos para gerar compras
    @FXML
    private void gerarRelatorioComprasDiarias() {
        // Placeholder: Lógica para gerar relatório de compras diário
        showAlert("Gerar Relatório de Compras", "Relatório de compras diárias gerado!");
    }

    @FXML
    private void gerarRelatorioComprasSemanais() {
        // Placeholder: Lógica para gerar relatório de compras semanal
        showAlert("Gerar Relatório de Compras", "Relatório de compras semanais gerado!");
    }

    @FXML
    private void gerarRelatorioComprasMensais() {
        // Placeholder: Lógica para gerar relatório de compras mensal
        showAlert("Gerar Relatório de Compras", "Relatório de compras mensais gerado!");
    }

    // Métodos para gerar estoque
    @FXML
    private void gerarEstoquePDF() {
        // Placeholder: Lógica para gerar relatório de estoque
        showAlert("Gerar Relatório de Estoque", "Relatório de estoque gerado!");
    }

    // Métodos para gerar histórico de movimentações
    @FXML
    private void gerarHistoricoMovimentacoesPDF() {
        // Placeholder: Lógica para gerar relatório de histórico de movimentações
        showAlert("Gerar Histórico de Movimentações", "Histórico de movimentações gerado!");
    }

    // Métodos para gerar mais vendidos
    @FXML
    private void gerarMaisVendidosPDF() {
        // Placeholder: Lógica para gerar relatório de mais vendidos
        showAlert("Gerar Relatório de Produtos Mais Vendidos", "Relatório de mais vendidos gerado!");
    }

    // Métodos para gerar menos movimentados
    @FXML
    private void gerarMenosMovimentadosPDF() {
        // Placeholder: Lógica para gerar relatório de menos movimentados
        showAlert("Gerar Relatório de Produtos Menos Movimentados", "Relatório de menos movimentados gerado!");
    }

    // Método para mostrar um alert de confirmação
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void gerarVendasPDF(){

    }
    @FXML
    private void gerarComprasPDF(){

    }

    // Botão Voltar
    @FXML
    public void voltar(ActionEvent event) {
        carregarTela("/com/gerencia/estoque/painel-prop/painel-prop.fxml", "Painel do Administrador", event);
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
