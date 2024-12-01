package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class RelatorioItensMaisDemandados {

    private final StringProperty produto;
    private final IntegerProperty quantidadeDemandada;

    public RelatorioItensMaisDemandados(String produto, int quantidadeDemandada) {
        this.produto = new SimpleStringProperty(produto);
        this.quantidadeDemandada = new SimpleIntegerProperty(quantidadeDemandada);
    }

    // Getters e setters para as propriedades observáveis
    public StringProperty produtoProperty() {
        return produto;
    }

    public IntegerProperty quantidadeDemandadaProperty() {
        return quantidadeDemandada;
    }

    // Getters convencionais
    public String getProduto() {
        return produto.get();
    }

    public void setProduto(String produto) {
        this.produto.set(produto);
    }

    public int getQuantidadeDemandada() {
        return quantidadeDemandada.get();
    }

    public void setQuantidadeDemandada(int quantidadeDemandada) {
        this.quantidadeDemandada.set(quantidadeDemandada);
    }
}

