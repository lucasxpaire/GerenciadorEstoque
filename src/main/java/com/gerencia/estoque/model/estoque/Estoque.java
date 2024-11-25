package com.gerencia.estoque.model.estoque;

public class Estoque {
    private int idProduto;
    private double preco;
    private String descricao;
    private int quantidade;

    public Estoque(int idProduto, double preco, String descricao, int quantidade) {
        this.idProduto = idProduto;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Estoque{" +
                "idProduto=" + idProduto +
                ", preco=" + preco +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}
