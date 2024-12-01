package com.gerencia.estoque.controller.relatorio;

import com.gerencia.estoque.model.relatorio.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

import com.gerencia.estoque.dao.Database;
import java.sql.*;

public class RelatoriosController {

    // Tabelas
    @FXML private TableView<RelatorioVisaoGeral> tabelaVisaoGeral;
    @FXML private TableView<RelatorioTransacoes> tabelaTransacoes;
    @FXML private TableView<RelatorioEstoque> tabelaEstoque;
    @FXML private TableView<RelatorioClientes> tabelaClientes;
    @FXML private TableView<RelatorioDescontos> tabelaDescontos;
    @FXML private TableView<RelatorioFuncionarios> tabelaFuncionarios;
    @FXML private TableView<RelatorioDemandas> tabelaDemandas;

    // Colunas da Tabela Visão Geral
    @FXML private TableColumn<RelatorioVisaoGeral, String> colunaTotalVendas;
    @FXML private TableColumn<RelatorioVisaoGeral, String> colunaTotalReceitas;
    @FXML private TableColumn<RelatorioVisaoGeral, String> colunaProdutosMaisVendidos;
    @FXML private TableColumn<RelatorioVisaoGeral, String> colunaEstoqueMinimo;

    // Colunas da Tabela Transações
    @FXML private TableColumn<RelatorioTransacoes, String> colunaDataTransacao;
    @FXML private TableColumn<RelatorioTransacoes, String> colunaDescricaoProduto;
    @FXML private TableColumn<RelatorioTransacoes, Integer> colunaQuantidadeTransacao;
    @FXML private TableColumn<RelatorioTransacoes, Double> colunaValorTransacao;
    @FXML private TableColumn<RelatorioTransacoes, String> colunaFuncionarioTransacao;

    // Colunas da Tabela Estoque
    @FXML private TableColumn<RelatorioEstoque, String> colunaProdutoEstoque;
    @FXML private TableColumn<RelatorioEstoque, Integer> colunaQuantidadeEstoque;
    @FXML private TableColumn<RelatorioEstoque, Double> colunaPrecoEstoque;

    // Colunas da Tabela Clientes
    @FXML private TableColumn<RelatorioClientes, String> colunaClienteNome;
    @FXML private TableColumn<RelatorioClientes, Integer> colunaClientePontos;
    @FXML private TableColumn<RelatorioClientes, String> colunaUsoDesconto;

    // Colunas da Tabela Descontos
    @FXML private TableColumn<RelatorioDescontos, String> colunaTipoDesconto;
    @FXML private TableColumn<RelatorioDescontos, Double> colunaValorDesconto;
    @FXML private TableColumn<RelatorioDescontos, Double> colunaPercentualDesconto;

    // Colunas da Tabela Funcionários
    @FXML private TableColumn<RelatorioFuncionarios, String> colunaFuncionarioNome;
    @FXML private TableColumn<RelatorioFuncionarios, Integer> colunaFuncionarioVendas;
    @FXML private TableColumn<RelatorioFuncionarios, Double> colunaFuncionarioValorVendas;

    // Colunas da Tabela Demandas
    @FXML private TableColumn<RelatorioDemandas, String> colunaProdutoDemandado;
    @FXML private TableColumn<RelatorioDemandas, Integer> colunaQuantidadeDemandada;
    @FXML private TableColumn<RelatorioDemandas, String> colunaAtendimentoDemanda;

    // Botão Voltar
    @FXML private Button voltarButton;

    // Métodos de Inicialização
    @FXML
    public void initialize() {
        // Carregar os dados das tabelas
        carregarDadosVisaoGeral();
        carregarDadosTransacoes();
        carregarDadosEstoque();
        carregarDadosClientes();
        carregarDadosDescontos();
        carregarDadosFuncionarios();
        carregarDadosDemandas();
    }

    // Método para carregar os dados de cada relatório
    private void carregarDadosVisaoGeral() {
        ObservableList<RelatorioVisaoGeral> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT SUM(t.Preco * t.Quantidade) AS total_vendas, " +
                    "SUM(t.Preco) AS total_receitas, " +
                    "GROUP_CONCAT(p.Descricao ORDER BY SUM(t.Quantidade) DESC LIMIT 5) AS produtos_mais_vendidos, " +
                    "e.Quantidade AS estoque_minimo " +
                    "FROM Transacao t " +
                    "JOIN Produto p ON t.IdProduto = p.IdProduto " +
                    "JOIN Estoque e ON e.IdProduto = p.IdProduto " +
                    "GROUP BY e.Quantidade";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioVisaoGeral(
                        rs.getString("total_vendas"),
                        rs.getString("total_receitas"),
                        rs.getString("produtos_mais_vendidos"),
                        rs.getString("estoque_minimo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaVisaoGeral.setItems(dados);
        colunaTotalVendas.setCellValueFactory(cellData -> cellData.getValue().totalVendasProperty());
        colunaTotalReceitas.setCellValueFactory(cellData -> cellData.getValue().totalReceitasProperty());
        colunaProdutosMaisVendidos.setCellValueFactory(cellData -> cellData.getValue().produtosMaisVendidosProperty());
        colunaEstoqueMinimo.setCellValueFactory(cellData -> cellData.getValue().estoqueMinimoProperty());
    }

    private void carregarDadosTransacoes() {
        ObservableList<RelatorioTransacoes> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT t.DataHora AS data_transacao, p.Descricao AS descricao_produto, t.Quantidade, " +
                    "t.Preco, f.Nome AS funcionario_nome " +
                    "FROM Transacao t " +
                    "JOIN Produto p ON t.IdProduto = p.IdProduto " +
                    "JOIN Funcionario f ON t.IdFuncionario = f.IdFuncionario";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioTransacoes(
                        rs.getString("data_transacao"),
                        rs.getString("descricao_produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("Preco"),
                        rs.getString("funcionario_nome")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaTransacoes.setItems(dados);
        colunaDataTransacao.setCellValueFactory(cellData -> cellData.getValue().dataTransacaoProperty());
        colunaDescricaoProduto.setCellValueFactory(cellData -> cellData.getValue().descricaoProdutoProperty());
        colunaQuantidadeTransacao.setCellValueFactory(cellData -> cellData.getValue().quantidadeTransacaoProperty().asObject());
        colunaValorTransacao.setCellValueFactory(cellData -> cellData.getValue().valorTransacaoProperty().asObject());
        colunaFuncionarioTransacao.setCellValueFactory(cellData -> cellData.getValue().funcionarioTransacaoProperty());
    }

    private void carregarDadosEstoque() {
        ObservableList<RelatorioEstoque> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT p.Descricao AS produto_nome, e.Quantidade AS quantidade_estoque, e.Preco AS preco_unitario " +
                    "FROM Estoque e " +
                    "JOIN Produto p ON e.IdProduto = p.IdProduto";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioEstoque(
                        rs.getString("produto_nome"),
                        rs.getInt("quantidade_estoque"),
                        rs.getDouble("preco_unitario")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaEstoque.setItems(dados);
        colunaProdutoEstoque.setCellValueFactory(cellData -> cellData.getValue().produtoEstoqueProperty());
        colunaQuantidadeEstoque.setCellValueFactory(cellData -> cellData.getValue().quantidadeEstoqueProperty().asObject());
        colunaPrecoEstoque.setCellValueFactory(cellData -> cellData.getValue().precoEstoqueProperty().asObject());
    }

    private void carregarDadosClientes() {
        ObservableList<RelatorioClientes> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT c.Nome AS nome_cliente, c.Pontos AS pontos_cliente, d.Tipo AS tipo_desconto " +
                    "FROM Cliente c " +
                    "LEFT JOIN Desconto d ON c.TipoDesconto = d.Tipo";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioClientes(
                        rs.getString("nome_cliente"),
                        rs.getInt("pontos_cliente"),
                        rs.getString("tipo_desconto")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaClientes.setItems(dados);
        colunaClienteNome.setCellValueFactory(cellData -> cellData.getValue().clienteNomeProperty());
        colunaClientePontos.setCellValueFactory(cellData -> cellData.getValue().clientePontosProperty().asObject());
        colunaUsoDesconto.setCellValueFactory(cellData -> cellData.getValue().usoDescontoProperty());
    }

    private void carregarDadosDescontos() {
        ObservableList<RelatorioDescontos> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT Tipo AS tipo_desconto, Valor AS valor_desconto, Percentual AS percentual_desconto " +
                    "FROM Desconto";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioDescontos(
                        rs.getString("tipo_desconto"),
                        rs.getDouble("valor_desconto"),
                        rs.getDouble("percentual_desconto")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaDescontos.setItems(dados);
        colunaTipoDesconto.setCellValueFactory(cellData -> cellData.getValue().tipoDescontoProperty());
        colunaValorDesconto.setCellValueFactory(cellData -> cellData.getValue().valorDescontoProperty().asObject());
        colunaPercentualDesconto.setCellValueFactory(cellData -> cellData.getValue().percentualDescontoProperty().asObject());
    }

    private void carregarDadosFuncionarios() {
        ObservableList<RelatorioFuncionarios> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT f.Nome AS nome_funcionario, COUNT(t.IdTransacao) AS numero_vendas, SUM(t.Preco * t.Quantidade) AS total_vendas " +
                    "FROM Funcionario f " +
                    "JOIN Transacao t ON f.IdFuncionario = t.IdFuncionario " +
                    "GROUP BY f.IdFuncionario";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioFuncionarios(
                        rs.getString("nome_funcionario"),
                        rs.getInt("numero_vendas"),
                        rs.getDouble("total_vendas")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaFuncionarios.setItems(dados);
        colunaFuncionarioNome.setCellValueFactory(cellData -> cellData.getValue().nomeFuncionarioProperty());
        colunaFuncionarioVendas.setCellValueFactory(cellData -> cellData.getValue().numeroVendasProperty().asObject());
        colunaFuncionarioValorVendas.setCellValueFactory(cellData -> cellData.getValue().totalVendasProperty().asObject());
    }

    private void carregarDadosDemandas() {
        ObservableList<RelatorioDemandas> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT i.Descricao AS produto_demandado, d.Contagem AS quantidade_demandada, c.Nome AS cliente_nome " +
                    "FROM Demanda d " +
                    "JOIN ItemDemandado i ON d.IdItem = i.IdItem " +
                    "JOIN Cliente c ON d.IdCliente = c.IdCliente";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioDemandas(
                        rs.getString("produto_demandado"),
                        rs.getInt("quantidade_demandada"),
                        rs.getString("cliente_nome")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaDemandas.setItems(dados);
        colunaProdutoDemandado.setCellValueFactory(cellData -> cellData.getValue().produtoDemandadoProperty());
        colunaQuantidadeDemandada.setCellValueFactory(cellData -> cellData.getValue().quantidadeDemandadaProperty().asObject());
        colunaAtendimentoDemanda.setCellValueFactory(cellData -> cellData.getValue().atendimentoDemandaProperty());
    }

    // Método para voltar
    @FXML
    public void voltar(MouseEvent event) {
        // Ação ao voltar (por exemplo, fechando a janela atual ou retornando ao menu)
    }
}
