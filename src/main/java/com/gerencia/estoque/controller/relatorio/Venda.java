package com.gerencia.estoque.controller.relatorio;

public class Venda {
    private String dataVenda;
    private String produtoVenda;
    private int quantidadeVenda;
    private double totalVenda;

    // Construtores, getters e setters

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getProdutoVenda() {
        return produtoVenda;
    }

    public void setProdutoVenda(String produtoVenda) {
        this.produtoVenda = produtoVenda;
    }

    public int getQuantidadeVenda() {
        return quantidadeVenda;
    }

    public void setQuantidadeVenda(int quantidadeVenda) {
        this.quantidadeVenda = quantidadeVenda;
    }

    public double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(double totalVenda) {
        this.totalVenda = totalVenda;
    }
}
