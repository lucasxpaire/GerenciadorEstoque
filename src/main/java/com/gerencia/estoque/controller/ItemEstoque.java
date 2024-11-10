package com.gerencia.estoque.controller;

public class ItemEstoque {

    private int id;
    private String nome;
    private String categoria;
    private int quantidade;
    private double preco;

    // Construtor com todos os parâmetros
    public ItemEstoque(int id, String nome, String categoria, int quantidade, double preco) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    // Setter para categoria, caso seja necessário
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Para exibir o nome no ComboBox e TableView
    @Override
    public String toString() {
        return nome; // Apenas o nome será exibido no ComboBox e TableView
    }
}
