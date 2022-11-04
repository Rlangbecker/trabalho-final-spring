package com.vemser.geekers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Desafio {

    private Integer idDesafio;
    private UsuarioEntity UsuarioEntity;
    private String pergunta;
    private Integer resposta;

    public Desafio() {

    }
}