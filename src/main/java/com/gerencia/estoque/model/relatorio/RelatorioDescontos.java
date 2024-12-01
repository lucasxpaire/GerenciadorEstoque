package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class RelatorioDescontos {
    private final StringProperty tipoDesconto;
    private final DoubleProperty valorDesconto;
    private final DoubleProperty percentualDesconto;

    public RelatorioDescontos(String tipoDesconto, double valorDesconto, double percentualDesconto) {
        this.tipoDesconto = new SimpleStringProperty(tipoDesconto);
        this.valorDesconto = new SimpleDoubleProperty(valorDesconto);
        this.percentualDesconto = new SimpleDoubleProperty(percentualDesconto);
    }

    public StringProperty tipoDescontoProperty() {
        return tipoDesconto;
    }

    public DoubleProperty valorDescontoProperty() {
        return valorDesconto;
    }

    public DoubleProperty percentualDescontoProperty() {
        return percentualDesconto;
    }
}