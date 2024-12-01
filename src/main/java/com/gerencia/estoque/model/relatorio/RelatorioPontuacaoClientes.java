package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.*;

public class RelatorioPontuacaoClientes {
    private final IntegerProperty idCliente;
    private final StringProperty nome;
    private final IntegerProperty pontuacao;
    private final IntegerProperty comprasFeitas;

    public RelatorioPontuacaoClientes(int idCliente, String nome, int pontuacao, int comprasFeitas) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.nome = new SimpleStringProperty(nome);
        this.pontuacao = new SimpleIntegerProperty(pontuacao);
        this.comprasFeitas = new SimpleIntegerProperty(comprasFeitas);
    }

    public IntegerProperty idClienteProperty() {
        return idCliente;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public IntegerProperty pontuacaoProperty() {
        return pontuacao;
    }

    public IntegerProperty comprasFeitasProperty() {
        return comprasFeitas;
    }

    // Getters para acesso direto aos valores
    public int getIdCliente() {
        return idCliente.get();
    }

    public String getNome() {
        return nome.get();
    }

    public int getPontuacao() {
        return pontuacao.get();
    }

    public int getComprasFeitas() {
        return comprasFeitas.get();
    }
}