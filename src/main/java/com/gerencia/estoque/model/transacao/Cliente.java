package com.gerencia.estoque.model.transacao;

public class Cliente {
    private int idCliente;
    private String nome;
    private String cpf;

    public Cliente(int idCliente, String nome, String cpf) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpf = cpf;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString() {
        return nome + " (" + cpf + ")";
    }
}
