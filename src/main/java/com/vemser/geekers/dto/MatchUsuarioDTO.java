package com.vemser.geekers.dto;


import com.vemser.geekers.entity.UsuarioEntity;
import lombok.Data;

@Data
public class MatchUsuarioDTO {
    private Integer idMatch;
    private UsuarioEntity usuarioEntity;
    private UsuarioEntity usuarioEntityMain;
}
