package com.vemser.geekers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioMatchDTO {

    private Integer idUsuario;
    private String nome;
    private String email;
    private Integer idMatch;
    private Integer usuarioMain;
}
