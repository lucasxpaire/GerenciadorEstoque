package com.gerencia.estoque.model.transacao;

public class Funcionario {

    private int idFuncionario;
    private String nome;

    // Construtor
    public Funcionario(int idFuncionario, String nome) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
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

    @Override
    public String toString() {
        return nome;  // Isto garantirá que o nome do funcionário seja exibido no ComboBox
    }
}
