package com.gerencia.estoque.model.estoque;

import javafx.beans.property.*;

public class EstoqueProduto {
    private final IntegerProperty idProd;
    private final StringProperty descricao;
    private final DoubleProperty preco;
    private final IntegerProperty quantidade;

    public EstoqueProduto(int idProd, String descricao, double preco, int quantidade) {
        this.idProd = new SimpleIntegerProperty(idProd);
        this.descricao = new SimpleStringProperty(descricao);
        this.preco = new SimpleDoubleProperty(preco);
        this.quantidade = new SimpleIntegerProperty(quantidade);
    }

    public int getIdProd() {
        return idProd.get();
    }

    public IntegerProperty idProdProperty() {
        return idProd;
    }

    public String getDescricao() {
        return descricao.get();
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public double getPreco() {
        return preco.get();
    }

    public DoubleProperty precoProperty() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade.get();
    }

    public IntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }
}
