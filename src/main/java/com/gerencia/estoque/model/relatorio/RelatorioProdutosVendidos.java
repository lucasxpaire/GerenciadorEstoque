package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RelatorioProdutosVendidos {
    private StringProperty nomeProduto;
    private IntegerProperty quantidadeVendida;
    private DoubleProperty precoTotal;

    public RelatorioProdutosVendidos(String nomeProduto, Integer quantidadeVendida, Double precoTotal) {
        this.nomeProduto = new SimpleStringProperty(nomeProduto);
        this.quantidadeVendida = new SimpleIntegerProperty(quantidadeVendida);
        this.precoTotal = new SimpleDoubleProperty(precoTotal);
    }

    // Getters e Setters para os campos
    public String getNomeProduto() {
        return nomeProduto.get();
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto.set(nomeProduto);
    }

    public StringProperty nomeProdutoProperty() {
        return nomeProduto;
    }

    public Integer getQuantidadeVendida() {
        return quantidadeVendida.get();
    }

    public void setQuantidadeVendida(Integer quantidadeVendida) {
        this.quantidadeVendida.set(quantidadeVendida);
    }

    public IntegerProperty quantidadeVendidaProperty() {
        return quantidadeVendida;
    }

    public Double getPrecoTotal() {
        return precoTotal.get();
    }

    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal.set(precoTotal);
    }

    public DoubleProperty precoTotalProperty() {
        return precoTotal;
    }
}
