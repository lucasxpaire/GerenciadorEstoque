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
    @FXML private TableView<RelatorioTransacoes> tabelaTransacoes;
    @FXML private TableView<RelatorioEstoque> tabelaEstoque;
    @FXML private TableView<RelatorioClientes> tabelaClientes;
    @FXML private TableView<RelatorioDescontos> tabelaDescontos;
    @FXML private TableView<RelatorioFuncionarios> tabelaFuncionarios;
    @FXML private TableView<RelatorioDemandas> tabelaDemandas;



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
        // Configura as colunas da tabela
        colunaTotalVendas.setCellValueFactory(cellData -> cellData.getValue().totalVendasProperty().asObject());
        colunaProdutoMaisVendido.setCellValueFactory(cellData -> cellData.getValue().produtoMaisVendidoProperty());
        colunaQuantidadeMaisVendida.setCellValueFactory(cellData -> cellData.getValue().quantidadeMaisVendidaProperty().asObject());
        colunaProdutoComEstoqueBaixo.setCellValueFactory(cellData -> cellData.getValue().produtoComEstoqueBaixoProperty());
        colunaQuantidadeEstoqueBaixo.setCellValueFactory(cellData -> cellData.getValue().quantidadeEstoqueBaixoProperty().asObject());

        // Adiciona a lista de dados na tabela
        tabelaVisaoGeral.setItems(dadosRelatorio);
        // Carrega os dados (se necessário)
        carregarDadosVisaoGeral();


        carregarDadosTransacoes();
        carregarDadosEstoque();
        carregarDadosClientes();
        carregarDadosDescontos();
        carregarDadosFuncionarios();
        carregarDadosDemandas();
    }

    // Declaração da tabela para exibição dos dados
    @FXML
    private TableView<RelatorioVisaoGeral> tabelaVisaoGeral;

    // Colunas da tabela
    @FXML
    private TableColumn<RelatorioVisaoGeral, Double> colunaTotalVendas;
    @FXML
    private TableColumn<RelatorioVisaoGeral, String> colunaProdutoMaisVendido;
    @FXML
    private TableColumn<RelatorioVisaoGeral, Integer> colunaQuantidadeMaisVendida;
    @FXML
    private TableColumn<RelatorioVisaoGeral, String> colunaProdutoComEstoqueBaixo;
    @FXML
    private TableColumn<RelatorioVisaoGeral, Integer> colunaQuantidadeEstoqueBaixo;

    // Coleção de dados que será exibida na tabela
    private ObservableList<RelatorioVisaoGeral> dadosRelatorio = FXCollections.observableArrayList();


    // Método para carregar os dados de cada relatório
    private void carregarDadosVisaoGeral() {
        ObservableList<RelatorioVisaoGeral> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            // Total de vendas no período
            String sqlVendas = "SELECT SUM(Preco * Quantidade) AS total_vendas " +
                    "FROM Transacao " +
                    "WHERE Tipo = 'venda' AND DataHora BETWEEN ? AND ?";

            // Produto mais vendido
            String sqlProdutoMaisVendido = "SELECT p.Descricao, SUM(t.Quantidade) AS total_vendido " +
                    "FROM Transacao t " +
                    "JOIN Produto p ON t.IdProduto = p.IdProduto " +
                    "WHERE t.Tipo = 'venda' " +
                    "GROUP BY p.Descricao " +
                    "ORDER BY total_vendido DESC " +
                    "LIMIT 1";

            // Estoque com itens abaixo do nível mínimo
            String sqlEstoqueBaixo = "SELECT Descricao, Quantidade " +
                    "FROM Estoque " +
                    "WHERE Quantidade < ? " +
                    "ORDER BY Quantidade ASC";

            double totalVendas = 0.0;
            String produtoMaisVendido = "Nenhum produto vendido";
            int quantidadeMaisVendida = 0;
            String produtoComEstoqueBaixo = "Estoque adequado";
            int quantidadeEstoqueBaixo = 0;

            // Total de vendas no período
            try (PreparedStatement stmtVendas = connection.prepareStatement(sqlVendas)) {
                stmtVendas.setTimestamp(1, Timestamp.valueOf("2024-01-01 00:00:00")); // Exemplo: início do período
                stmtVendas.setTimestamp(2, Timestamp.valueOf("2024-12-31 23:59:59")); // Exemplo: fim do período
                ResultSet rsVendas = stmtVendas.executeQuery();
                if (rsVendas.next()) {
                    totalVendas = rsVendas.getDouble("total_vendas");
                }
            }

            // Produto mais vendido
            try (PreparedStatement stmtProdutoMaisVendido = connection.prepareStatement(sqlProdutoMaisVendido)) {
                ResultSet rsProdutoMaisVendido = stmtProdutoMaisVendido.executeQuery();
                if (rsProdutoMaisVendido.next()) {
                    produtoMaisVendido = rsProdutoMaisVendido.getString("Descricao");
                    quantidadeMaisVendida = rsProdutoMaisVendido.getInt("total_vendido");
                }
            }

            // Estoque com itens abaixo do nível mínimo
            try (PreparedStatement stmtEstoqueBaixo = connection.prepareStatement(sqlEstoqueBaixo)) {
                stmtEstoqueBaixo.setInt(1, 10); // Exemplo: nível mínimo de estoque
                ResultSet rsEstoqueBaixo = stmtEstoqueBaixo.executeQuery();
                if (rsEstoqueBaixo.next()) {
                    produtoComEstoqueBaixo = rsEstoqueBaixo.getString("Descricao");
                    quantidadeEstoqueBaixo = rsEstoqueBaixo.getInt("Quantidade");
                }
            }

            dados.add(new RelatorioVisaoGeral(
                    totalVendas,
                    produtoMaisVendido,
                    quantidadeMaisVendida,
                    produtoComEstoqueBaixo,
                    quantidadeEstoqueBaixo
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Adicionando os dados à tabela ou outra interface visual
        tabelaVisaoGeral.setItems(dados);
        colunaTotalVendas.setCellValueFactory(cellData -> cellData.getValue().totalVendasProperty().asObject());
        colunaProdutoMaisVendido.setCellValueFactory(cellData -> cellData.getValue().produtoMaisVendidoProperty());
        colunaQuantidadeMaisVendida.setCellValueFactory(cellData -> cellData.getValue().quantidadeMaisVendidaProperty().asObject());
        colunaProdutoComEstoqueBaixo.setCellValueFactory(cellData -> cellData.getValue().produtoComEstoqueBaixoProperty());
        colunaQuantidadeEstoqueBaixo.setCellValueFactory(cellData -> cellData.getValue().quantidadeEstoqueBaixoProperty().asObject());
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

    // Colunas da Tabela Descontos
    @FXML private TableColumn<RelatorioDescontos, String> colunaTipoDesconto;
    @FXML private TableColumn<RelatorioDescontos, Double> colunaValorDesconto;
    @FXML private TableColumn<RelatorioDescontos, Double> colunaPercentualDesconto;

    private void carregarDadosDescontos() {
        ObservableList<RelatorioDescontos> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT Tipo AS tipo_desconto, Percentual AS percentual_desconto, PontosMinimos AS pontos_minimos " +
                    "FROM Desconto";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioDescontos(
                        rs.getString("tipo_desconto"),
                        rs.getDouble("percentual_desconto"),
                        rs.getInt("pontos_minimos") // Aqui você pode decidir como tratar os pontos mínimos
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