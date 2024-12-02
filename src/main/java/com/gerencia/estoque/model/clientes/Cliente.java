package com.gerencia.estoque.model.clientes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Cliente {
    private IntegerProperty idCliente;
    private StringProperty nome;
    private StringProperty cpf;

    // Construtor
    public Cliente(int idCliente, String nome, String cpf) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.nome = new SimpleStringProperty(nome);
        this.cpf = new SimpleStringProperty(cpf);
    }

    // Propriedades para Bindings (JavaFX)
    public IntegerProperty idClienteProperty() {
        return idCliente;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty cpfProperty() {
        return cpf;
    }

    // Getters e Setters para compatibilidade
    public int getIdCliente() {
        return idCliente.get();
    }

    public void setIdCliente(int idCliente) {
        this.idCliente.set(idCliente);
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getCpf() {
        return cpf.get();
    }

    public void setCpf(String cpf) {
        this.cpf.set(cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente.get() +
                ", nome='" + nome.get() + '\'' +
                ", cpf='" + cpf.get() + '\'' +
                '}';
    }
}