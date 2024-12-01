package com.gerencia.estoque.controller.relatorio;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.relatorio.RelatorioDescontos;
import com.gerencia.estoque.model.relatorio.RelatorioDemanda;
import com.gerencia.estoque.model.relatorio.RelatorioPontuacaoClientes;
import com.gerencia.estoque.model.relatorio.RelatorioTransacoes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RelatoriosController {

    // Tabelas
    @FXML private TableView<RelatorioTransacoes> tabelaTransacoes;
    @FXML private TableView<RelatorioDemanda> tabelaDemanda;
    @FXML private TableView<RelatorioDescontos> tabelaDescontos;

    // Colunas da Tabela Transações
    @FXML private TableColumn<RelatorioTransacoes, Integer> colunaIdTransacao;
    @FXML private TableColumn<RelatorioTransacoes, String> colunaProduto;
    @FXML private TableColumn<RelatorioTransacoes, Integer> colunaQuantidade;
    @FXML private TableColumn<RelatorioTransacoes, Double> colunaPreco;
    @FXML private TableColumn<RelatorioTransacoes, String> colunaDataHora;

    // Colunas da Tabela Demanda
    @FXML private TableColumn<RelatorioDemanda, String> colunaClienteDemanda;
    @FXML private TableColumn<RelatorioDemanda, String> colunaItemDemandado;
    @FXML private TableColumn<RelatorioDemanda, Integer> colunaQuantidadeDemandada;
    @FXML private TableColumn<RelatorioDemanda, String> colunaUltimaDataDemanda;

    // Colunas da Tabela Descontos
    @FXML private TableColumn<RelatorioDescontos, Integer> colunaIdDesconto;
    @FXML private TableColumn<RelatorioDescontos, String> colunaDescricaoDesconto;
    @FXML private TableColumn<RelatorioDescontos, Integer> colunaQtdAplicada;

    // Colunas da Tabela Pontuação de Clientes
    @FXML private TableView<RelatorioPontuacaoClientes> tabelaPontuacaoClientes;
    @FXML private TableColumn<RelatorioPontuacaoClientes, Integer> colunaIdClientePontuacao;
    @FXML private TableColumn<RelatorioPontuacaoClientes, String> colunaNomeClientePontuacao;
    @FXML private TableColumn<RelatorioPontuacaoClientes, Integer> colunaPontuacao;
    @FXML private TableColumn<RelatorioPontuacaoClientes, Integer> colunaComprasFeitas; // Nova coluna

    @FXML private TableColumn<RelatorioPontuacaoClientes, Integer> colunaVendasAcimaDe10;


    // Botões
    @FXML private Button btnMaisVendidos;
    @FXML private Button btnMaisComprados;

    // Métodos de Inicialização
    @FXML
    public void initialize() {
        carregarDadosTransacoes();
        carregarDadosDemanda();
        carregarDadosDescontos();
        carregarDadosPontuacaoClientes();
    }

    // Método para carregar dados de transações
    private void carregarDadosTransacoes() {
        ObservableList<RelatorioTransacoes> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT IdTransacao, Descricao, Quantidade, Preco, DataHora FROM Transacao";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioTransacoes(
                        rs.getInt("IdTransacao"),
                        rs.getString("Descricao"),
                        rs.getInt("Quantidade"),
                        rs.getDouble("Preco"),
                        rs.getString("DataHora")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaTransacoes.setItems(dados);
        colunaIdTransacao.setCellValueFactory(cellData -> cellData.getValue().idTransacaoProperty().asObject());
        colunaProduto.setCellValueFactory(cellData -> cellData.getValue().produtoProperty());
        colunaQuantidade.setCellValueFactory(cellData -> cellData.getValue().quantidadeProperty().asObject());
        colunaPreco.setCellValueFactory(cellData -> cellData.getValue().precoProperty().asObject());
        colunaDataHora.setCellValueFactory(cellData -> cellData.getValue().dataHoraProperty());
    }

    // Método para carregar dados de demanda
    private void carregarDadosDemanda() {
        ObservableList<RelatorioDemanda> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT c.Nome AS cliente, i.Descricao AS item, d.Contagem AS quantidade, d.DataHora AS ultima_data " +
                    "FROM Demanda d " +
                    "JOIN ItemDemandado i ON d.IdItem = i.IdItem " +
                    "JOIN Cliente c ON d.IdCliente = c.IdCliente";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioDemanda(
                        rs.getString("cliente"),
                        rs.getString("item"),
                        rs.getInt("quantidade"),
                        rs.getString("ultima_data")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaDemanda.setItems(dados);
        colunaClienteDemanda.setCellValueFactory(cellData -> cellData.getValue().clienteProperty());
        colunaItemDemandado.setCellValueFactory(cellData -> cellData.getValue().itemDemandadoProperty());
        colunaQuantidadeDemandada.setCellValueFactory(cellData -> cellData.getValue().quantidadeProperty().asObject());
        colunaUltimaDataDemanda.setCellValueFactory(cellData -> cellData.getValue().ultimaDataProperty());
    }

    // Método para carregar dados de descontos
    private void carregarDadosDescontos() {
        ObservableList<RelatorioDescontos> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT IdDesconto, Descricao, PontosMinimos FROM Desconto";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioDescontos(
                        rs.getInt("IdDesconto"),
                        rs.getString("Descricao"),
                        rs.getInt("PontosMinimos")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaDescontos.setItems(dados);
        colunaIdDesconto.setCellValueFactory(cellData -> cellData.getValue().idDescontoProperty().asObject());
        colunaDescricaoDesconto.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());
        colunaQtdAplicada.setCellValueFactory(cellData -> cellData.getValue().pontosMinimosProperty().asObject());
    }

    // Método para carregar dados de pontuação de clientes
    // Método para carregar dados de pontuação de clientes
    private void carregarDadosPontuacaoClientes() {
        ObservableList<RelatorioPontuacaoClientes> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT c.IdCliente, c.Nome, f.Pontos AS pontuacao, " +
                    "(SELECT COUNT(*) FROM Transacao t WHERE t.IdCliente = c.IdCliente) AS compras_feitas " + // Atualização da consulta
                    "FROM Cliente c " +
                    "JOIN Fidelidade f ON c.IdCliente = f.IdCliente";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioPontuacaoClientes(
                        rs.getInt("IdCliente"),
                        rs.getString("Nome"),
                        rs.getInt("pontuacao"),
                        rs.getInt("compras_feitas") // Atualização para incluir compras feitas
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaPontuacaoClientes.setItems(dados);
        colunaIdClientePontuacao.setCellValueFactory(cellData -> cellData.getValue().idClienteProperty().asObject());
        colunaNomeClientePontuacao.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        colunaPontuacao.setCellValueFactory(cellData -> cellData.getValue().pontuacaoProperty().asObject());
        colunaComprasFeitas.setCellValueFactory(cellData -> cellData.getValue().comprasFeitasProperty().asObject()); // Atualização para a nova coluna
    }

    // Métodos para ações dos botões
    @FXML
    private void handleMaisVendidos(MouseEvent event) {
        // Implementar lógica para ver mais vendidos
    }

    @FXML
    private void handleMaisComprados(MouseEvent event) {
        // Implementar lógica para ver mais comprados
    }
}