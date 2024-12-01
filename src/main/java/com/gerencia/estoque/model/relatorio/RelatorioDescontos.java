package com.gerencia.estoque.model.relatorio;

import javafx.beans.property.*;

public class RelatorioDescontos {
    private final IntegerProperty idDesconto;
    private final StringProperty descricao;
    private final IntegerProperty pontosMinimos;

    public RelatorioDescontos(int idDesconto, String descricao, int pontosMinimos) {
        this.idDesconto = new SimpleIntegerProperty(idDesconto);
        this.descricao = new SimpleStringProperty(descricao);
        this.pontosMinimos = new SimpleIntegerProperty(pontosMinimos);
    }

    public IntegerProperty idDescontoProperty() {
        return idDesconto;
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public IntegerProperty pontosMinimosProperty() {
        return pontosMinimos;
    }
}