package com.gerencia.estoque.model.estoque;

import java.time.LocalDateTime;

public class Demanda {
    private int idItem;
    private int idCliente;
    private LocalDateTime dataHora;

    public Demanda(int idItem, int idCliente, LocalDateTime dataHora) {
        this.idItem = idItem;
        this.idCliente = idCliente;
        this.dataHora = dataHora;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "Demanda{" +
                "idItem=" + idItem +
                ", idCliente=" + idCliente +
                ", dataHora=" + dataHora +
                '}';
    }
}
