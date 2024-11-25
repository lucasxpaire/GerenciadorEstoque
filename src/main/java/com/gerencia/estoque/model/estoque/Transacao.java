package com.gerencia.estoque.model.estoque;

import java.time.LocalDateTime;

public class Transacao {
    private int idTransacao;
    private int idProduto;
    private int idFuncionario;
    private double preco;
    private String descricao;
    private int quantidade;
    private LocalDateTime dataHora;
    private String tipo;

    public Transacao(int idTransacao, int idProduto, int idFuncionario, double preco, String descricao, int quantidade, LocalDateTime dataHora, String tipo) {
        this.idTransacao = idTransacao;
        this.idProduto = idProduto;
        this.idFuncionario = idFuncionario;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.dataHora = dataHora;
        this.tipo = tipo;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "idTransacao=" + idTransacao +
                ", idProduto=" + idProduto +
                ", idFuncionario=" + idFuncionario +
                ", preco=" + preco +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", dataHora=" + dataHora +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
