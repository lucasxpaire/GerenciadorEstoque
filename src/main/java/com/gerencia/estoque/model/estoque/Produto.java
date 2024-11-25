package com.gerencia.estoque.model.estoque;

public class Produto {
    private int idProduto;
    private double preco;
    private String descricao;

    public Produto(int idProduto, double preco, String descricao) {
        this.idProduto = idProduto;
        this.preco = preco;
        this.descricao = descricao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", preco=" + preco +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
