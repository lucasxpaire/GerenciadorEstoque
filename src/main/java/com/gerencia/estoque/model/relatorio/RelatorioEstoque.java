package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class RelatorioEstoque {
    private final StringProperty produtoEstoque;
    private final IntegerProperty quantidadeEstoque;
    private final DoubleProperty precoEstoque;

    public RelatorioEstoque(String produtoEstoque, int quantidadeEstoque, double precoEstoque) {
        this.produtoEstoque = new SimpleStringProperty(produtoEstoque);
        this.quantidadeEstoque = new SimpleIntegerProperty(quantidadeEstoque);
        this.precoEstoque = new SimpleDoubleProperty(precoEstoque);
    }

    public StringProperty produtoEstoqueProperty() {
        return produtoEstoque;
    }

    public IntegerProperty quantidadeEstoqueProperty() {
        return quantidadeEstoque;
    }

    public DoubleProperty precoEstoqueProperty() {
        return precoEstoque;
    }
}

