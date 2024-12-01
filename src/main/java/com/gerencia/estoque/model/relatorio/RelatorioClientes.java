package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelatorioClientes {
    private final StringProperty clienteNome;
    private final IntegerProperty clientePontos;
    private final StringProperty usoDesconto;

    public RelatorioClientes(String clienteNome, int clientePontos, String usoDesconto) {
        this.clienteNome = new SimpleStringProperty(clienteNome);
        this.clientePontos = new SimpleIntegerProperty(clientePontos);
        this.usoDesconto = new SimpleStringProperty(usoDesconto);
    }

    public StringProperty clienteNomeProperty() {
        return clienteNome;
    }

    public IntegerProperty clientePontosProperty() {
        return clientePontos;
    }

    public StringProperty usoDescontoProperty() {
        return usoDesconto;
    }
}
