package com.gerencia.estoque.model.estoque;

import javafx.beans.property.*;

public class Produto {
    private final IntegerProperty idProd;
    private final StringProperty descricao;
    private final DoubleProperty preco;

    public Produto(int idProd, String descricao, double preco) {
        this.idProd = new SimpleIntegerProperty(idProd);
        this.descricao = new SimpleStringProperty(descricao);
        this.preco = new SimpleDoubleProperty(preco);
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
}
