package com.vemser.geekers.dto;

import lombok.Data;

@Data
public class UsuarioSeguroDTO {

    private String nome;
    private String email;
    private String token;
}
