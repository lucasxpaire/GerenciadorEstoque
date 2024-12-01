package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RelatorioFuncionarios {

    private StringProperty nomeFuncionario;
    private IntegerProperty numeroVendas;
    private DoubleProperty totalVendas;

    public RelatorioFuncionarios(String nomeFuncionario, int numeroVendas, double totalVendas) {
        this.nomeFuncionario = new SimpleStringProperty(nomeFuncionario);
        this.numeroVendas = new SimpleIntegerProperty(numeroVendas);
        this.totalVendas = new SimpleDoubleProperty(totalVendas);
    }

    // Métodos getters e setters para cada propriedade

    public String getNomeFuncionario() {
        return nomeFuncionario.get();
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario.set(nomeFuncionario);
    }

    public StringProperty nomeFuncionarioProperty() {
        return nomeFuncionario;
    }

    public int getNumeroVendas() {
        return numeroVendas.get();
    }

    public void setNumeroVendas(int numeroVendas) {
        this.numeroVendas.set(numeroVendas);
    }

    public IntegerProperty numeroVendasProperty() {
        return numeroVendas;
    }

    public double getTotalVendas() {
        return totalVendas.get();
    }

    public void setTotalVendas(double totalVendas) {
        this.totalVendas.set(totalVendas);
    }

    public DoubleProperty totalVendasProperty() {
        return totalVendas;
    }
}
