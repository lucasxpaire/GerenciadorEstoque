package com.gerencia.estoque.controller.relatorio;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.relatorio.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelatoriosController {

    // Tabelas
    @FXML private TableView<RelatorioTransacoes> tabelaTransacoes;
    @FXML private TableView<RelatorioDemanda> tabelaDemanda;
    @FXML private TableView<RelatorioDescontos> tabelaDescontos;
    @FXML private TableView<RelatorioMetricas> tabelaProdutosMaisVendidos;
    @FXML private TableView<RelatorioMetricas> tabelaProdutosMaisComprados;
    @FXML private TableView<RelatorioItensMaisDemandados> tabelaItensMaisDemandados;

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

    // Colunas da Tabela Metricas
    @FXML
    private TableColumn<RelatorioMetricas, String> colunaProdutoVendidos;
    @FXML
    private TableColumn<RelatorioMetricas, Integer> colunaQuantidadeVendida;
    @FXML
    private TableColumn<RelatorioMetricas, String> colunaProdutoComprado;
    @FXML
    private TableColumn<RelatorioMetricas, Integer> colunaQuantidadeComprada;

    @FXML TableColumn<RelatorioItensMaisDemandados, String> colunaItemDemandadoMaisRequisitado;
    @FXML TableColumn<RelatorioItensMaisDemandados, Integer> colunaQuantidadeDemandadaTotal;


    // Métodos de Inicialização
    @FXML
    public void initialize() {
        formatarColunaDataTransacao(colunaDataHora);
        formatarColunaDataDemanda(colunaUltimaDataDemanda);

        carregarDadosTransacoes();
        carregarDadosDemanda();
        carregarDadosDescontos();
        carregarDadosPontuacaoClientes();
        calcularProdutosMaisVendidos();
        calcularProdutosMaisComprados();
        calcularItensMaisDemandados();

        centralizarColunas();
        colunaIdTransacao.setVisible(false);
        colunaIdClientePontuacao.setVisible(false);
        colunaIdDesconto.setVisible(false);
    }

    // Método para configurar o alinhamento das colunas das Tabelas
    private void centralizarColunas() {
        // Centraliza as colunas da Tabela de Transações
        colunaIdTransacao.setStyle("-fx-alignment: CENTER;");
        colunaProduto.setStyle("-fx-alignment: CENTER;");
        colunaQuantidade.setStyle("-fx-alignment: CENTER;");
        colunaPreco.setStyle("-fx-alignment: CENTER;");
        colunaDataHora.setStyle("-fx-alignment: CENTER;");

        // Centraliza as colunas da Tabela de Demanda
        colunaClienteDemanda.setStyle("-fx-alignment: CENTER;");
        colunaItemDemandado.setStyle("-fx-alignment: CENTER;");
        colunaQuantidadeDemandada.setStyle("-fx-alignment: CENTER;");
        colunaUltimaDataDemanda.setStyle("-fx-alignment: CENTER;");

        // Centraliza as colunas da Tabela de Descontos
        colunaIdDesconto.setStyle("-fx-alignment: CENTER;");
        colunaDescricaoDesconto.setStyle("-fx-alignment: CENTER;");
        colunaQtdAplicada.setStyle("-fx-alignment: CENTER;");

        // Centraliza as colunas da Tabela de Pontuação de Clientes
        colunaIdClientePontuacao.setStyle("-fx-alignment: CENTER;");
        colunaNomeClientePontuacao.setStyle("-fx-alignment: CENTER;");
        colunaPontuacao.setStyle("-fx-alignment: CENTER;");
        colunaComprasFeitas.setStyle("-fx-alignment: CENTER;");

        // Centraliza as colunas da Tabela Métricas
        colunaProdutoVendidos.setStyle("-fx-alignment: CENTER;");
        colunaQuantidadeVendida.setStyle("-fx-alignment: CENTER;");
        colunaProdutoComprado.setStyle("-fx-alignment: CENTER;");
        colunaQuantidadeComprada.setStyle("-fx-alignment: CENTER;");

        // Centraliza as colunas da Tabela de Itens Mais Demandados
        colunaItemDemandadoMaisRequisitado.setStyle("-fx-alignment: CENTER;");
        colunaQuantidadeDemandadaTotal.setStyle("-fx-alignment: CENTER;");
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

    private void formatarColunaDataTransacao(TableColumn<RelatorioTransacoes, String> coluna) {
        coluna.setCellFactory(new Callback<TableColumn<RelatorioTransacoes, String>, TableCell<RelatorioTransacoes, String>>() {
            @Override
            public TableCell<RelatorioTransacoes, String> call(TableColumn<RelatorioTransacoes, String> param) {
                return new TableCell<RelatorioTransacoes, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            // Remove a parte dos nanossegundos da string de data
                            String dataFormatada = item.split("\\.")[0]; // Divide a string na parte dos nanossegundos
                            setText(dataFormatada); // Exibe a data sem os nanossegundos
                        }
                    }
                };
            }
        });
    }

    private void formatarColunaDataDemanda(TableColumn<RelatorioDemanda, String> coluna) {
        coluna.setCellFactory(new Callback<TableColumn<RelatorioDemanda, String>, TableCell<RelatorioDemanda, String>>() {
            @Override
            public TableCell<RelatorioDemanda, String> call(TableColumn<RelatorioDemanda, String> param) {
                return new TableCell<RelatorioDemanda, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            // Remove a parte dos nanossegundos da string de data
                            String dataFormatada = item.split("\\.")[0]; // Divide a string na parte dos nanossegundos
                            setText(dataFormatada); // Exibe a data sem os nanossegundos
                        }
                    }
                };
            }
        });
    }

    // Método para calcular os produtos mais vendidos
    private void calcularProdutosMaisVendidos() {
        ObservableList<RelatorioMetricas> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            // Ajustando a consulta SQL para pegar apenas vendas (Tipo = 'Venda')
            String sql = "SELECT Descricao, SUM(Quantidade) AS total_vendido " +
                    "FROM Transacao " +
                    "WHERE Tipo = 'Venda' " +  // Filtra transações do tipo "Venda"
                    "GROUP BY Descricao " +
                    "ORDER BY total_vendido DESC";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioMetricas(
                        rs.getString("Descricao"),
                        rs.getInt("total_vendido"),
                        0 // Não é necessário para esta tabela
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaProdutosMaisVendidos.setItems(dados);
        colunaProdutoVendidos.setCellValueFactory(cellData -> cellData.getValue().produtoProperty());
        colunaQuantidadeVendida.setCellValueFactory(cellData -> cellData.getValue().quantidadeVendidaProperty().asObject());
    }


    // Método para calcular os produtos mais comprados
    private void calcularProdutosMaisComprados() {
        ObservableList<RelatorioMetricas> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            // Ajustando a consulta SQL para pegar apenas compras (Tipo = 'Compra')
            String sql = "SELECT Descricao, SUM(Quantidade) AS total_comprado " +
                    "FROM Transacao " +
                    "WHERE Tipo = 'Compra' " +  // Filtra transações do tipo "Compra"
                    "GROUP BY Descricao " +
                    "ORDER BY total_comprado DESC";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioMetricas(
                        rs.getString("Descricao"),
                        0, // Não é necessário para esta tabela
                        rs.getInt("total_comprado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaProdutosMaisComprados.setItems(dados);
        colunaProdutoComprado.setCellValueFactory(cellData -> cellData.getValue().produtoProperty());
        colunaQuantidadeComprada.setCellValueFactory(cellData -> cellData.getValue().quantidadeCompradaProperty().asObject());
    }

    // Método para calcular os itens mais demandados por cada cliente
    private void calcularItensMaisDemandados() {
        ObservableList<RelatorioItensMaisDemandados> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            // Consulta para pegar os itens mais demandados por cada cliente
            String sql = "SELECT i.Descricao AS item, SUM(d.Contagem) AS total_demandado " +
                    "FROM Demanda d " +
                    "JOIN ItemDemandado i ON d.IdItem = i.IdItem " +
                    "GROUP BY i.Descricao " +
                    "ORDER BY total_demandado DESC";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Preenchendo a lista de dados com os resultados da consulta
            while (rs.next()) {
                dados.add(new RelatorioItensMaisDemandados(
                        rs.getString("item"), // Descrição do item
                        rs.getInt("total_demandado") // Total demandado do item
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Verifica se a tabela espera um ObservableList de RelatorioItensMaisDemandados
        tabelaItensMaisDemandados.setItems(dados);

        // Definição das células da tabela (supondo que 'produtoProperty' e 'quantidadeDemandadaProperty' existam)
        colunaItemDemandadoMaisRequisitado.setCellValueFactory(cellData -> cellData.getValue().produtoProperty());
        colunaQuantidadeDemandadaTotal.setCellValueFactory(cellData -> cellData.getValue().quantidadeDemandadaProperty().asObject());
    }
}