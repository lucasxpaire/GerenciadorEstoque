package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.*;

public class RelatorioDemanda {
    private final StringProperty cliente;
    private final StringProperty itemDemandado;
    private final IntegerProperty quantidade;
    private final StringProperty ultimaData;

    public RelatorioDemanda(String cliente, String itemDemandado, int quantidade, String ultimaData) {
        this.cliente = new SimpleStringProperty(cliente);
        this.itemDemandado = new SimpleStringProperty(itemDemandado);
        this.quantidade = new SimpleIntegerProperty(quantidade);
        this.ultimaData = new SimpleStringProperty(ultimaData);
    }

    public StringProperty clienteProperty() {
        return cliente;
    }

    public StringProperty itemDemandadoProperty() {
        return itemDemandado;
    }

    public IntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public StringProperty ultimaDataProperty() {
        return ultimaData;
    }
}