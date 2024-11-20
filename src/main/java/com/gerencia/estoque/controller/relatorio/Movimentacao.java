package com.gerencia.estoque.controller.relatorio;

public class Movimentacao {
    private String dataMovimento;
    private String produtoMovimento;
    private int quantidadeMovimento;
    private String tipoMovimento;

    public String getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(String dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public String getProdutoMovimento() {
        return produtoMovimento;
    }

    public void setProdutoMovimento(String produtoMovimento) {
        this.produtoMovimento = produtoMovimento;
    }

    public int getQuantidadeMovimento() {
        return quantidadeMovimento;
    }

    public void setQuantidadeMovimento(int quantidadeMovimento) {
        this.quantidadeMovimento = quantidadeMovimento;
    }

    public String getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }
// Construtores, getters e setters
}

