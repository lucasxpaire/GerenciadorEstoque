package com.gerencia.estoque.controller.funcionarios;

public class Credencial {

    private int idCredencial;
    private String usuario;
    private String senha;
    private String tipo;

    // Construtor
    public Credencial(int idCredencial, String usuario, String senha, String tipo) {
        this.idCredencial = idCredencial;
        this.usuario = usuario;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters e Setters
    public int getIdCredencial() {
        return idCredencial;
    }

    public void setIdCredencial(int idCredencial) {
        this.idCredencial = idCredencial;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
