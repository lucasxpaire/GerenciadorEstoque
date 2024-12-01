package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RelatorioMetricas {

    private final StringProperty produto;
    private final IntegerProperty quantidadeVendida;
    private final IntegerProperty quantidadeComprada;

    public RelatorioMetricas(String produto, int quantidadeVendida, int quantidadeComprada) {
        this.produto = new SimpleStringProperty(produto);
        this.quantidadeVendida = new SimpleIntegerProperty(quantidadeVendida);
        this.quantidadeComprada = new SimpleIntegerProperty(quantidadeComprada);
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
}
