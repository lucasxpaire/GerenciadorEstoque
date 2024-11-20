package com.gerencia.estoque.controller.relatorio;

public class Compra {
    private String dataCompra;
    private String produtoCompra;
    private int quantidadeCompra;
    private double totalCompra;

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getProdutoCompra() {
        return produtoCompra;
    }

    public void setProdutoCompra(String produtoCompra) {
        this.produtoCompra = produtoCompra;
    }

    public int getQuantidadeCompra() {
        return quantidadeCompra;
    }

    public void setQuantidadeCompra(int quantidadeCompra) {
        this.quantidadeCompra = quantidadeCompra;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }
// Construtores, getters e setters
}
