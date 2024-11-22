package com.gerencia.estoque.model.relatorio;

public class Estoque {
    private String produtoEstoque;
    private int quantidadeEstoque;
    private double valorEstoque;

    // Construtores, getters e setters

    public String getProdutoEstoque() {
        return produtoEstoque;
    }

    public void setProdutoEstoque(String produtoEstoque) {
        this.produtoEstoque = produtoEstoque;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public double getValorEstoque() {
        return valorEstoque;
    }

    public void setValorEstoque(double valorEstoque) {
        this.valorEstoque = valorEstoque;
    }
}
