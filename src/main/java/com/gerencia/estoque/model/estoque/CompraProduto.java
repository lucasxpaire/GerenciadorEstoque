package com.gerencia.estoque.model.estoque;

import javafx.beans.property.*;

public class CompraProduto {

    private final IntegerProperty idProd;
    private final IntegerProperty idCompra;
    private final IntegerProperty quantidade;
    private final StringProperty descricao;
    private final DoubleProperty preco;

    // Construtor
    public CompraProduto(int idProd, int idCompra, int quantidade, String descricao, double preco) {
        this.idProd = new SimpleIntegerProperty(idProd);
        this.idCompra = new SimpleIntegerProperty(idCompra);
        this.quantidade = new SimpleIntegerProperty(quantidade);
        this.descricao = new SimpleStringProperty(descricao);
        this.preco = new SimpleDoubleProperty(preco);
    }

    // Getters e Setters
    public int getIdProd() {
        return idProd.get();
    }

    public IntegerProperty idProdProperty() {
        return idProd;
    }

    public int getIdCompra() {
        return idCompra.get();
    }

    public IntegerProperty idCompraProperty() {
        return idCompra;
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
