package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RelatorioMetricas {

    private final StringProperty produto;
    private final IntegerProperty quantidadeVendida;
    private final IntegerProperty quantidadeComprada;
    private final DoubleProperty precoTotal;

    public RelatorioMetricas(String produto, int quantidadeVendida, int quantidadeComprada, double precoTotal) {
        this.produto = new SimpleStringProperty(produto);
        this.quantidadeVendida = new SimpleIntegerProperty(quantidadeVendida);
        this.quantidadeComprada = new SimpleIntegerProperty(quantidadeComprada);
        this.precoTotal = new SimpleDoubleProperty(precoTotal);
    }

    // Getters e Setters
    public String getProduto() {
        return produto.get();
    }

    public void setProduto(String produto) {
        this.produto.set(produto);
    }

    public StringProperty produtoProperty() {
        return produto;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida.get();
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida.set(quantidadeVendida);
    }

    public IntegerProperty quantidadeVendidaProperty() {
        return quantidadeVendida;
    }

    public int getQuantidadeComprada() {
        return quantidadeComprada.get();
    }

    public void setQuantidadeComprada(int quantidadeComprada) {
        this.quantidadeComprada.set(quantidadeComprada);
    }

    public IntegerProperty quantidadeCompradaProperty() {
        return quantidadeComprada;
    }

    public double getPrecoTotal() {
        return precoTotal.get();
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal.set(precoTotal);
    }

    public DoubleProperty precoTotalProperty() {
        return precoTotal;
    }
}
