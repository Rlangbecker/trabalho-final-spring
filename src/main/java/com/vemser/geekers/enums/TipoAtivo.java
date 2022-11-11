package com.vemser.geekers.enums;

public enum TipoAtivo {
    ATIVO("S"),
    INATIVO("N");

    private final String descricao;

    TipoAtivo(String descricao) {
        this.descricao = descricao;
    }

    public String toString() {
        return descricao;
    }
}
