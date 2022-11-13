package com.vemser.geekers.dto;

import lombok.Data;

@Data
public class UsuarioMatchDadosDTO {
    private Integer idUsuario;
    private String nomeUsuario;
    private Integer idUsuarioLogado;
    private String nomeUsuarioLogado;
}
