package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class RelatorioTransacoes {
    private final StringProperty dataTransacao;
    private final StringProperty descricaoProduto;
    private final IntegerProperty quantidadeTransacao;
    private final DoubleProperty valorTransacao;
    private final StringProperty funcionarioTransacao;

    public RelatorioTransacoes(String dataTransacao, String descricaoProduto, int quantidadeTransacao, double valorTransacao, String funcionarioTransacao) {
        this.dataTransacao = new SimpleStringProperty(dataTransacao);
        this.descricaoProduto = new SimpleStringProperty(descricaoProduto);
        this.quantidadeTransacao = new SimpleIntegerProperty(quantidadeTransacao);
        this.valorTransacao = new SimpleDoubleProperty(valorTransacao);
        this.funcionarioTransacao = new SimpleStringProperty(funcionarioTransacao);
    }

    public StringProperty dataTransacaoProperty() {
        return dataTransacao;
    }

    public StringProperty descricaoProdutoProperty() {
        return descricaoProduto;
    }

    public IntegerProperty quantidadeTransacaoProperty() {
        return quantidadeTransacao;
    }

    public DoubleProperty valorTransacaoProperty() {
        return valorTransacao;
    }

    public StringProperty funcionarioTransacaoProperty() {
        return funcionarioTransacao;
    }
}

