package com.gerencia.estoque.controller.relatorio;

import com.gerencia.estoque.dao.Database;
import com.gerencia.estoque.model.relatorio.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RelatoriosController {
    @FXML private TableView<RelatorioTransacoes> tabelaVendas;
    @FXML private TableView<RelatorioTransacoes> tabelaCompra;
    @FXML private TableColumn<RelatorioTransacoes, Integer> colunaIdTransacao;
    @FXML private TableColumn<RelatorioTransacoes, String> colunaProduto;
    @FXML private TableColumn<RelatorioTransacoes, Integer> colunaQuantidade;
    @FXML private TableColumn<RelatorioTransacoes, Double> colunaPreco;
    @FXML private TableColumn<RelatorioTransacoes, String> colunaDataHora;

    @FXML private TableColumn<RelatorioTransacoes, Integer> colunaIdTransacaoCompra;
    @FXML private TableColumn<RelatorioTransacoes, String> colunaProdutoCompra;
    @FXML private TableColumn<RelatorioTransacoes, Integer> colunaQuantidadeCompra;
    @FXML private TableColumn<RelatorioTransacoes, Double> colunaPrecoCompra;
    @FXML private TableColumn<RelatorioTransacoes, String> colunaDataHoraCompra;

    @FXML private TableView<RelatorioDemanda> tabelaDemanda;
    @FXML private TableColumn<RelatorioDemanda, String> colunaClienteDemanda;
    @FXML private TableColumn<RelatorioDemanda, String> colunaItemDemandado;
    @FXML private TableColumn<RelatorioDemanda, Integer> colunaQuantidadeDemandada;
    @FXML private TableColumn<RelatorioDemanda, String> colunaUltimaDataDemanda;

    @FXML private TableView<RelatorioDescontos> tabelaDescontos;
    @FXML private TableColumn<RelatorioDescontos, Integer> colunaIdDesconto;
    @FXML private TableColumn<RelatorioDescontos, String> colunaDescricaoDesconto;
    @FXML private TableColumn<RelatorioDescontos, Integer> colunaQtdAplicada;

    @FXML private TableView<RelatorioMetricas> tabelaProdutosMaisVendidos;
    @FXML private TableView<RelatorioMetricas> tabelaProdutosMaisComprados;
    @FXML private TableColumn<RelatorioMetricas, String> colunaProdutoVendidos;
    @FXML private TableColumn<RelatorioMetricas, Integer> colunaQuantidadeVendidaMetricas;
    @FXML private TableColumn<RelatorioMetricas, Double> colunaPrecoTotalVendidos;
    @FXML private TableColumn<RelatorioMetricas, String> colunaProdutoComprado;
    @FXML private TableColumn<RelatorioMetricas, Integer> colunaQuantidadeComprada;
    @FXML private TableColumn<RelatorioMetricas, Double> colunaPrecoTotalComprados;

    @FXML private TableView<RelatorioItensMaisDemandados> tabelaItensMaisDemandados;
    @FXML TableColumn<RelatorioItensMaisDemandados, String> colunaItemDemandadoMaisRequisitado;
    @FXML TableColumn<RelatorioItensMaisDemandados, Integer> colunaQuantidadeDemandadaTotal;

    @FXML private TableView<RelatorioDescontosAplicados> tabelaDescontosMaisAplicados;
    @FXML private TableColumn<RelatorioDescontosAplicados, String> colunaDescricaoDescontoAplicado;
    @FXML private TableColumn<RelatorioDescontosAplicados, Integer> colunaQuantidadeAplicada;

    @FXML
    public void initialize() {
        formatarColunaDataTransacao(colunaDataHora);
        formatarColunaDataTransacao(colunaDataHoraCompra);

        formatarColunaDataDemanda(colunaUltimaDataDemanda);

        carregarDadosTransacoes();
        carregarDadosDemanda();
        carregarDadosDescontos();
        calcularProdutosMaisVendidos();
        calcularProdutosMaisComprados();
        calcularItensMaisDemandados();
        carregarDescontosMaisAplicados();

        centralizarColunas();
        colunaIdTransacao.setVisible(false);
        colunaIdDesconto.setVisible(false);
    }

    // Método para configurar o alinhamento das colunas das Tabelas
    private void centralizarColunas() {
        // Centraliza as colunas da Tabela de Vendas
        colunaIdTransacao.setStyle("-fx-alignment: CENTER;");
        colunaProduto.setStyle("-fx-alignment: CENTER;");
        colunaQuantidade.setStyle("-fx-alignment: CENTER;");
        colunaPreco.setStyle("-fx-alignment: CENTER;");
        colunaDataHora.setStyle("-fx-alignment: CENTER;");

        // Centraliza as colunas da Tabela de Compras
        colunaIdTransacaoCompra.setStyle("-fx-alignment: CENTER;");
        colunaProdutoCompra.setStyle("-fx-alignment: CENTER;");
        colunaQuantidadeCompra.setStyle("-fx-alignment: CENTER;");
        colunaPrecoCompra.setStyle("-fx-alignment: CENTER;");
        colunaDataHoraCompra.setStyle("-fx-alignment: CENTER;");

        // Centraliza as colunas da Tabela de Demanda
        colunaClienteDemanda.setStyle("-fx-alignment: CENTER;");
        colunaItemDemandado.setStyle("-fx-alignment: CENTER;");
        colunaQuantidadeDemandada.setStyle("-fx-alignment: CENTER;");
        colunaUltimaDataDemanda.setStyle("-fx-alignment: CENTER;");

        // Centraliza as colunas da Tabela de Descontos
        colunaIdDesconto.setStyle("-fx-alignment: CENTER;");
        colunaDescricaoDesconto.setStyle("-fx-alignment: CENTER;");
        colunaQtdAplicada.setStyle("-fx-alignment: CENTER;");


        // Centraliza as colunas da Tabela Métricas
        colunaProdutoVendidos.setStyle("-fx-alignment: CENTER;");
        colunaQuantidadeVendidaMetricas.setStyle("-fx-alignment: CENTER;");
        colunaPrecoTotalVendidos.setStyle("-fx-alignment: CENTER;");

        colunaProdutoComprado.setStyle("-fx-alignment: CENTER;");
        colunaQuantidadeComprada.setStyle("-fx-alignment: CENTER;");
        colunaPrecoTotalComprados.setStyle("-fx-alignment: CENTER;");

        // Centraliza as colunas da Tabela de Itens Mais Demandados
        colunaItemDemandadoMaisRequisitado.setStyle("-fx-alignment: CENTER;");
        colunaQuantidadeDemandadaTotal.setStyle("-fx-alignment: CENTER;");

        colunaDescricaoDescontoAplicado.setStyle("-fx-alignment: CENTER;");
        colunaQuantidadeAplicada.setStyle("-fx-alignment: CENTER;");
    }

    private void carregarDadosTransacoes() {
        ObservableList<RelatorioTransacoes> dadosVendas = FXCollections.observableArrayList();
        ObservableList<RelatorioTransacoes> dadosCompras = FXCollections.observableArrayList();

        try (Connection connection = Database.getConnection()) {
            // Dados de Transações Vendas
            String sqlVendas = "SELECT IdTransacao, Descricao, Quantidade, Preco, DataHora FROM Transacao WHERE Tipo = 'Venda'";
            Statement stmt = connection.createStatement();
            ResultSet rsVendas = stmt.executeQuery(sqlVendas);
            while (rsVendas.next()) {
                dadosVendas.add(new RelatorioTransacoes(
                        rsVendas.getInt("IdTransacao"),
                        rsVendas.getString("Descricao"),
                        rsVendas.getInt("Quantidade"),
                        rsVendas.getDouble("Preco"),
                        rsVendas.getString("DataHora")
                ));
            }

            // Dados de Transações Compras
            String sqlCompras = "SELECT IdTransacao, Descricao, Quantidade, Preco, DataHora FROM Transacao WHERE Tipo = 'Compra'";
            ResultSet rsCompras = stmt.executeQuery(sqlCompras);
            while (rsCompras.next()) {
                dadosCompras.add(new RelatorioTransacoes(
                        rsCompras.getInt("IdTransacao"),
                        rsCompras.getString("Descricao"),
                        rsCompras.getInt("Quantidade"),
                        rsCompras.getDouble("Preco"),
                        rsCompras.getString("DataHora")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Configura dados na tabela de Vendas
        tabelaVendas.setItems(dadosVendas);
        colunaIdTransacao.setCellValueFactory(cellData -> cellData.getValue().idTransacaoProperty().asObject());
        colunaProduto.setCellValueFactory(cellData -> cellData.getValue().produtoProperty());
        colunaQuantidade.setCellValueFactory(cellData -> cellData.getValue().quantidadeProperty().asObject());
        colunaPreco.setCellValueFactory(cellData -> cellData.getValue().precoProperty().asObject());
        colunaDataHora.setCellValueFactory(cellData -> cellData.getValue().dataHoraProperty());

        // Configura dados na tabela de Compras
        tabelaCompra.setItems(dadosCompras);
        colunaIdTransacaoCompra.setCellValueFactory(cellData -> cellData.getValue().idTransacaoProperty().asObject());
        colunaProdutoCompra.setCellValueFactory(cellData -> cellData.getValue().produtoProperty());
        colunaQuantidadeCompra.setCellValueFactory(cellData -> cellData.getValue().quantidadeProperty().asObject());
        colunaPrecoCompra.setCellValueFactory(cellData -> cellData.getValue().precoProperty().asObject());
        colunaDataHoraCompra.setCellValueFactory(cellData -> cellData.getValue().dataHoraProperty());
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
            String sql = "SELECT Descricao, SUM(Quantidade) AS total_vendido, SUM(Preco * Quantidade) AS preco_total " +
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
                        0, // Não é necessário para esta tabela
                        rs.getDouble("preco_total") // Adicionando o preço total
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaProdutosMaisVendidos.setItems(dados);
        colunaProdutoVendidos.setCellValueFactory(cellData -> cellData.getValue().produtoProperty());
        colunaQuantidadeVendidaMetricas.setCellValueFactory(cellData -> cellData.getValue().quantidadeVendidaProperty().asObject());
        colunaPrecoTotalVendidos.setCellValueFactory(cellData -> cellData.getValue().precoTotalProperty().asObject());
    }

    // Método para calcular os produtos mais comprados
    private void calcularProdutosMaisComprados() {
        ObservableList<RelatorioMetricas> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            // Ajustando a consulta SQL para pegar apenas compras (Tipo = 'Compra')
            String sql = "SELECT Descricao, SUM(Quantidade) AS total_comprado, SUM(Preco * Quantidade) AS preco_total " +
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
                        rs.getInt("total_comprado"),
                        rs.getDouble("preco_total") // Adicionando o preço total
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaProdutosMaisComprados.setItems(dados);
        colunaProdutoComprado.setCellValueFactory(cellData -> cellData.getValue().produtoProperty());
        colunaQuantidadeComprada.setCellValueFactory(cellData -> cellData.getValue().quantidadeCompradaProperty().asObject());
        colunaPrecoTotalComprados.setCellValueFactory(cellData -> cellData.getValue().precoTotalProperty().asObject());
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

    // Método para carregar dados de descontos aplicados
    private void carregarDescontosMaisAplicados() {
        ObservableList<RelatorioDescontosAplicados> dados = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            // Consulta para pegar os descontos aplicados nas transações com o campo IdDesconto não nulo
            String sql = "SELECT d.Descricao, COUNT(t.IdTransacao) AS quantidade_aplicada " +
                    "FROM Transacao t " +
                    "JOIN Desconto d ON t.IdDesconto = d.IdDesconto " + // Verifica se o desconto foi aplicado
                    "WHERE t.IdDesconto IS NOT NULL " +  // Filtra as transações com desconto
                    "GROUP BY d.Descricao " + // Agrupa pela descrição do desconto
                    "ORDER BY quantidade_aplicada DESC"; // Opcional: ordena os descontos mais aplicados

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dados.add(new RelatorioDescontosAplicados(
                        rs.getString("Descricao"), // Obtém a descrição do desconto
                        rs.getInt("quantidade_aplicada") // Obtém a quantidade de vezes que o desconto foi aplicado
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabelaDescontosMaisAplicados.setItems(dados);
        colunaDescricaoDescontoAplicado.setCellValueFactory(cellData -> cellData.getValue().descricaoDescontoProperty());
        colunaQuantidadeAplicada.setCellValueFactory(cellData -> cellData.getValue().quantidadeAplicadaProperty().asObject());
    }

    @FXML
    private void voltar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gerencia/estoque/painel-prop/painel-prop.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gerarPdfVendas(ActionEvent event){
        exportarTabelaParaPDF(tabelaVendas, "Relatório de Vendas", "Vendas.pdf");
    }
    @FXML
    private void gerarPdfCompras(ActionEvent event){
        exportarTabelaParaPDF(tabelaCompra, "Relatório de Compras", "Compras.pdf");
    }
    @FXML
    private void gerarPdfDemandas(ActionEvent event){
        exportarTabelaParaPDF(tabelaDemanda, "Relatório de Demandas", "ProdutosDemandados.pdf");
    }
    @FXML
    private void gerarPdfMaisDemandas(ActionEvent event){
        exportarTabelaParaPDF(tabelaItensMaisDemandados, "Relatório de Demandas", "ProdutosMaisDemandados.pdf");
    }

    @FXML
    private void gerarPdfDescontos(ActionEvent event){
        exportarTabelaParaPDF(tabelaDescontos, "Relatório de Descontos", "Descontos.pdf");
    }
    @FXML
    private void gerarPdfMaisDescontos(ActionEvent event){
        exportarTabelaParaPDF(tabelaDescontosMaisAplicados, "Relatório de Descontos", "DescontosMaisAplicados.pdf");
    }
    @FXML
    private void gerarPdfProdutosVendidos(ActionEvent event){
        exportarTabelaParaPDF(tabelaProdutosMaisVendidos, "Relatório de Produtos mais Vendidos", "ProdutosMaisVendidos.pdf");
    }
    @FXML
    private void gerarPdfProdutosComprados(ActionEvent event){
        exportarTabelaParaPDF(tabelaCompra, "Relatório de Produtos mais Comprados", "ProdutosMaisComprados.pdf");
    }

    private <T> void exportarTabelaParaPDF(TableView<T> tabela, String tituloPDF, String nomeArquivo) {
        try {
            // Caminho base para salvar os PDFs
            URL recurso = getClass().getClassLoader().getResource("com/gerencia/estoque/pdf");

            if (recurso == null) {
                // Se não encontrar o recurso, usamos a pasta 'pdf' no sistema de arquivos local
                String diretorioBase = "src/main/resources/com/gerencia/estoque/pdf"; // Caminho local no ambiente de desenvolvimento
                File diretorio = new File(diretorioBase);
                if (!diretorio.exists()) {
                    diretorio.mkdirs(); // Cria o diretório se não existir
                }
                String caminhoArquivo = diretorioBase + "/" + nomeArquivo;
                // Criação do documento PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
                document.open();

                // Adiciona o título do relatório
                document.add(new Paragraph(tituloPDF));
                document.add(new Paragraph(" ")); // Espaço em branco

                // Criação da tabela PDF com o número de colunas igual ao da TableView
                PdfPTable pdfTable = new PdfPTable(tabela.getColumns().size());
                pdfTable.setWidthPercentage(100); // Largura da tabela ajustada

                // Adiciona os cabeçalhos das colunas
                for (TableColumn<?, ?> coluna : tabela.getColumns()) {
                    pdfTable.addCell(coluna.getText());
                }

                // Adiciona os dados das células (itens)
                for (T item : tabela.getItems()) {
                    for (TableColumn<T, ?> coluna : tabela.getColumns()) {
                        // Aqui, dependendo do tipo da coluna, obtemos o valor de forma segura
                        Object valor = coluna.getCellData(item);
                        String valorStr = "";

                        // Tratamento de tipos comuns, adaptando conforme necessário para cada coluna
                        if (valor != null) {
                            if (valor instanceof StringProperty) {
                                valorStr = ((StringProperty) valor).get();
                            } else if (valor instanceof IntegerProperty) {
                                valorStr = String.valueOf(((IntegerProperty) valor).get());
                            } else {
                                valorStr = valor.toString();
                            }
                        }

                        // Adiciona a célula ao PDF com o valor convertido
                        pdfTable.addCell(valorStr);
                    }
                }

                // Adiciona a tabela ao documento PDF
                document.add(pdfTable);
                document.close();

                // Exibe o caminho onde o PDF foi gerado
                System.out.println("PDF gerado em: " + caminhoArquivo);
            } else {
                // Caso o recurso seja encontrado, utilizamos o URL
                String diretorioBase = recurso.getPath();
                File diretorio = new File(diretorioBase);
                if (!diretorio.exists()) {
                    diretorio.mkdirs(); // Cria o diretório se não existir
                }
                String caminhoArquivo = diretorioBase + "/" + nomeArquivo;

                // Criação do documento PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
                document.open();

                // Adiciona o título do relatório
                document.add(new Paragraph(tituloPDF));
                document.add(new Paragraph(" ")); // Espaço em branco

                // Criação da tabela PDF com o número de colunas igual ao da TableView
                PdfPTable pdfTable = new PdfPTable(tabela.getColumns().size());
                pdfTable.setWidthPercentage(100); // Largura da tabela ajustada

                // Adiciona os cabeçalhos das colunas
                for (TableColumn<?, ?> coluna : tabela.getColumns()) {
                    pdfTable.addCell(coluna.getText());
                }

                // Adiciona os dados das células (itens)
                for (T item : tabela.getItems()) {
                    for (TableColumn<T, ?> coluna : tabela.getColumns()) {
                        // Aqui, dependendo do tipo da coluna, obtemos o valor de forma segura
                        Object valor = coluna.getCellData(item);
                        String valorStr = "";

                        // Tratamento de tipos comuns, adaptando conforme necessário para cada coluna
                        if (valor != null) {
                            if (valor instanceof StringProperty) {
                                valorStr = ((StringProperty) valor).get();
                            } else if (valor instanceof IntegerProperty) {
                                valorStr = String.valueOf(((IntegerProperty) valor).get());
                            } else {
                                valorStr = valor.toString();
                            }
                        }

                        // Adiciona a célula ao PDF com o valor convertido
                        pdfTable.addCell(valorStr);
                    }
                }

                // Adiciona a tabela ao documento PDF
                document.add(pdfTable);
                document.close();

                System.out.println("PDF gerado em: " + caminhoArquivo);
            }

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


}