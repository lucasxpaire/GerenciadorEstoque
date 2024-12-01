package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelatorioDemandas {
    private final StringProperty produtoDemandado;
    private final IntegerProperty quantidadeDemandada;
    private final StringProperty atendimentoDemanda;

    public RelatorioDemandas(String produtoDemandado, int quantidadeDemandada, String atendimentoDemanda) {
        this.produtoDemandado = new SimpleStringProperty(produtoDemandado);
        this.quantidadeDemandada = new SimpleIntegerProperty(quantidadeDemandada);
        this.atendimentoDemanda = new SimpleStringProperty(atendimentoDemanda);
    }

    public StringProperty produtoDemandadoProperty() {
        return produtoDemandado;
    }

    public IntegerProperty quantidadeDemandadaProperty() {
        return quantidadeDemandada;
    }

    public StringProperty atendimentoDemandaProperty() {
        return atendimentoDemanda;
    }
}
