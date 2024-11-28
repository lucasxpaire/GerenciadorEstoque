package com.gerencia.estoque.model.desconto;

public class Desconto {

    private int idDesconto;
    private String tipo;
    private double percentual;
    private String descricao;

    // Construtor padrão
    public Desconto() {
    }

    // Construtor com parâmetros
    public Desconto(int idDesconto, String tipo, double percentual, String descricao) {
        this.idDesconto = idDesconto;
        this.tipo = tipo;
        this.percentual = percentual;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getIdDesconto() {
        return idDesconto;
    }

    public void setIdDesconto(int idDesconto) {
        this.idDesconto = idDesconto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        this.percentual = percentual;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Método toString para exibição personalizada (opcional)
    @Override
    public String toString() {
        return "Desconto{" +
                "idDesconto=" + idDesconto +
                ", tipo='" + tipo + '\'' +
                ", percentual=" + percentual +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
