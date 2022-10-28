package com.vemser.geekers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class Desafio {

    private Integer idDesafio;
    private Usuario Usuario;
    private String pergunta;
    private Integer resposta;

    public Desafio() {

    }
}