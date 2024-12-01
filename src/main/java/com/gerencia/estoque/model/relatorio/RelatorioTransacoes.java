package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.*;

public class RelatorioTransacoes {
    private final IntegerProperty idTransacao;
    private final StringProperty produto;
    private final IntegerProperty quantidade;
    private final DoubleProperty preco;
    private final StringProperty dataHora;

    public RelatorioTransacoes(int idTransacao, String produto, int quantidade, double preco, String dataHora) {
        this.idTransacao = new SimpleIntegerProperty(idTransacao);
        this.produto = new SimpleStringProperty(produto);
        this.quantidade = new SimpleIntegerProperty(quantidade);
        this.preco = new SimpleDoubleProperty(preco);
        this.dataHora = new SimpleStringProperty(dataHora);
    }

    public IntegerProperty idTransacaoProperty() {
        return idTransacao;
    }

    public StringProperty produtoProperty() {
        return produto;
    }

    public IntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public DoubleProperty precoProperty() {
        return preco;
    }

    public StringProperty dataHoraProperty() {
        return dataHora;
    }
}