package com.gerencia.estoque.controller.paineladm;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProdutosVisaoGeral {

    private StringProperty nome = new SimpleStringProperty();
    private IntegerProperty quantidadeVendida = new SimpleIntegerProperty();
    private DoubleProperty totalVendas = new SimpleDoubleProperty();

    public ProdutosVisaoGeral(String nome, int quantidadeVendida, double totalVendas) {
        this.nome.set(nome);
        this.quantidadeVendida.set(quantidadeVendida);
        this.totalVendas.set(totalVendas);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public IntegerProperty quantidadeVendidaProperty() {
        return quantidadeVendida;
    }

    public DoubleProperty totalVendasProperty() {
        return totalVendas;
    }
}
