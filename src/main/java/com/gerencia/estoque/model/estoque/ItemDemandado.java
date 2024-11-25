package com.gerencia.estoque.model.estoque;

public class ItemDemandado {
    private int idItem;
    private String descricao;

    public ItemDemandado(int idItem, String descricao) {
        this.idItem = idItem;
        this.descricao = descricao;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "ItemDemandado{" +
                "idItem=" + idItem +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
