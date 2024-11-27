package com.gerencia.estoque.model.transacao;

public class ItemResumo {
    private String descricao;
    private int quantidade;
    private double precoTotal;

    public ItemResumo(String descricao, int quantidade, double precoTotal) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }
}
