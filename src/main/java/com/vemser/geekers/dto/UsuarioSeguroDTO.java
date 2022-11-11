package com.vemser.geekers.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioSeguroDTO {

    private Integer idUsuario;
    private String login;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private String sexo;
}
