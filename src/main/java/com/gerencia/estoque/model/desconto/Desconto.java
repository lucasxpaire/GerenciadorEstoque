package com.gerencia.estoque.model.desconto;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Desconto {

    private IntegerProperty idDesconto;
    private StringProperty tipo;
    private DoubleProperty percentual;
    private StringProperty descricao;
    //private IntegerProperty pontosMinimos;

    private IntegerProperty pontosMinimos = new SimpleIntegerProperty();


    // Construtor
    public Desconto(int idDesconto, String tipo, double percentual, String descricao, int pontosMinimos) {
        this.idDesconto = new SimpleIntegerProperty(idDesconto);
        this.tipo = new SimpleStringProperty(tipo);
        this.percentual = new SimpleDoubleProperty(percentual);
        this.descricao = new SimpleStringProperty(descricao);
        this.pontosMinimos = new SimpleIntegerProperty(pontosMinimos);
    }

    // Getters e Setters
    public int getIdDesconto() {
        return idDesconto.get();
    }

    public void setIdDesconto(int idDesconto) {
        this.idDesconto.set(idDesconto);
    }

    public IntegerProperty idDescontoProperty() {
        return idDesconto;
    }

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public double getPercentual() {
        return percentual.get();
    }

    public void setPercentual(double percentual) {
        this.percentual.set(percentual);
    }

    public DoubleProperty percentualProperty() {
        return percentual;
    }

    public String getDescricao() {
        return descricao.get();
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public int getPontosMinimos() {
        return pontosMinimos.get();
    }

    public void setPontosMinimos(int pontosMinimos) {
        this.pontosMinimos.set(pontosMinimos);
    }

    public IntegerProperty pontosMinimosProperty() {
        return pontosMinimos;
    }
}
