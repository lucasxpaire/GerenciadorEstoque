package com.gerencia.estoque.model.transacao;

public class Produto {
    private int idProduto;
    private String descricao;
    private double preco;

    public Produto(int idProduto, String descricao, double preco) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.preco = preco;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return descricao + " - R$" + preco;
    }
}