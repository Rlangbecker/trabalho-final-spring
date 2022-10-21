package com.vemser.geekers.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Desafio {

    private Integer idDesafio;
    @NotBlank
    private String pergunta;
    @NotBlank
    @Size(max = 1)
    private String resposta;

    private Integer idUsuario;

    public Integer getIdDesafio() {
        return idDesafio;
    }

    public void setIdDesafio(Integer idDesafio) {
        this.idDesafio = idDesafio;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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