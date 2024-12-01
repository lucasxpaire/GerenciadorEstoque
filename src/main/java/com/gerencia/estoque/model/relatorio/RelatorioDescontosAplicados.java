package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class RelatorioDescontosAplicados {
    private final StringProperty descricaoDesconto;
    private final IntegerProperty quantidadeAplicada;

    public RelatorioDescontosAplicados(String descricaoDesconto, Integer quantidadeAplicada) {
        this.descricaoDesconto = new SimpleStringProperty(descricaoDesconto);
        this.quantidadeAplicada = new SimpleIntegerProperty(quantidadeAplicada);
    }

    public String getDescricaoDesconto() {
        return descricaoDesconto.get();
    }

    public void setDescricaoDesconto(String descricaoDesconto) {
        this.descricaoDesconto.set(descricaoDesconto);
    }

    public StringProperty descricaoDescontoProperty() {
        return descricaoDesconto;
    }

    public Integer getQuantidadeAplicada() {
        return quantidadeAplicada.get();
    }

    public void setQuantidadeAplicada(Integer quantidadeAplicada) {
        this.quantidadeAplicada.set(quantidadeAplicada);
    }

    public IntegerProperty quantidadeAplicadaProperty() {
        return quantidadeAplicada;
    }
}
