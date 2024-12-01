package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class RelatorioVisaoGeral {
    private final StringProperty totalVendas;
    private final StringProperty totalReceitas;
    private final StringProperty produtosMaisVendidos;
    private final StringProperty estoqueMinimo;

    public RelatorioVisaoGeral(String totalVendas, String totalReceitas, String produtosMaisVendidos, String estoqueMinimo) {
        this.totalVendas = new SimpleStringProperty(totalVendas);
        this.totalReceitas = new SimpleStringProperty(totalReceitas);
        this.produtosMaisVendidos = new SimpleStringProperty(produtosMaisVendidos);
        this.estoqueMinimo = new SimpleStringProperty(estoqueMinimo);
    }

    public StringProperty totalVendasProperty() {
        return totalVendas;
    }

    public StringProperty totalReceitasProperty() {
        return totalReceitas;
    }

    public StringProperty produtosMaisVendidosProperty() {
        return produtosMaisVendidos;
    }

    public StringProperty estoqueMinimoProperty() {
        return estoqueMinimo;
    }
}
