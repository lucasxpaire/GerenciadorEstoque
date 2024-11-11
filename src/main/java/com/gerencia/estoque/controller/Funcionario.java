package com.gerencia.estoque.controller;

public class Funcionario {

    private int idFuncionario;
    private String nome;
    private int idCredencial; // Referência à credencial (chave estrangeira)

    // Construtor
    public Funcionario(int idFuncionario, String nome, int idCredencial) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.idCredencial = idCredencial;
    }

    // Getters e Setters
    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdCredencial() {
        return idCredencial;
    }

    public void setIdCredencial(int idCredencial) {
        this.idCredencial = idCredencial;
    }
}
