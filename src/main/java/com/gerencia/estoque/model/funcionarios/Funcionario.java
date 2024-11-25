//package com.gerencia.estoque.model.funcionarios;
//
//public class Funcionario {
//
//    private int idFuncionario;
//    private String nome;
//    private int idCredencial; // Referência à credencial (chave estrangeira)
//
//    // Construtor
//    public Funcionario(int idFuncionario, String nome, int idCredencial) {
//        this.idFuncionario = idFuncionario;
//        this.nome = nome;
//        this.idCredencial = idCredencial;
//    }
//
//    // Getters e Setters
//    public int getIdFuncionario() {
//        return idFuncionario;
//    }
//
//    public void setIdFuncionario(int idFuncionario) {
//        this.idFuncionario = idFuncionario;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public int getIdCredencial() {
//        return idCredencial;
//    }
//
//    public void setIdCredencial(int idCredencial) {
//        this.idCredencial = idCredencial;
//    }
//}

//mudar para esse:
package com.gerencia.estoque.model.funcionarios;

public class Funcionario {
    private int idFuncionario;
    private int idCredencial;
    private String nome;

    public Funcionario(int idFuncionario, int idCredencial, String nome) {
        this.idFuncionario = idFuncionario;
        this.idCredencial = idCredencial;
        this.nome = nome;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getIdCredencial() {
        return idCredencial;
    }

    public void setIdCredencial(int idCredencial) {
        this.idCredencial = idCredencial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "idFuncionario=" + idFuncionario +
                ", idCredencial=" + idCredencial +
                ", nome='" + nome + '\'' +
                '}';
    }
}

