package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.*;

public class RelatorioVisaoGeral {
    private final DoubleProperty totalVendas;
    private final StringProperty produtoMaisVendido;
    private final IntegerProperty quantidadeMaisVendida;
    private final StringProperty produtoComEstoqueBaixo;
    private final IntegerProperty quantidadeEstoqueBaixo;

    public RelatorioVisaoGeral(double totalVendas, String produtoMaisVendido, int quantidadeMaisVendida,
                               String produtoComEstoqueBaixo, int quantidadeEstoqueBaixo) {
        this.totalVendas = new SimpleDoubleProperty(totalVendas);
        this.produtoMaisVendido = new SimpleStringProperty(produtoMaisVendido);
        this.quantidadeMaisVendida = new SimpleIntegerProperty(quantidadeMaisVendida);
        this.produtoComEstoqueBaixo = new SimpleStringProperty(produtoComEstoqueBaixo);
        this.quantidadeEstoqueBaixo = new SimpleIntegerProperty(quantidadeEstoqueBaixo);
    }

    public DoubleProperty totalVendasProperty() {
        return totalVendas;
    }

    public StringProperty produtoMaisVendidoProperty() {
        return produtoMaisVendido;
    }

    public IntegerProperty quantidadeMaisVendidaProperty() {
        return quantidadeMaisVendida;
    }

    public StringProperty produtoComEstoqueBaixoProperty() {
        return produtoComEstoqueBaixo;
    }

    public IntegerProperty quantidadeEstoqueBaixoProperty() {
        return quantidadeEstoqueBaixo;
    }
}
