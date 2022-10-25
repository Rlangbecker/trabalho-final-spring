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
    private Integer idUsuario;
    @NotBlank
    private String pergunta;
    @NotBlank
    @Size(max = 1)
    private String resposta;

    public Desafio() {

    }
    @Override
    public String toString() {
        return "Desafio{" +
                "idDesafio=" + idDesafio +
                ", pergunta='" + pergunta + '\'' +
                ", resposta='" + resposta + '\'' +
                '}';
    }
}