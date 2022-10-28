package com.vemser.geekers.dto;


import com.vemser.geekers.entity.Usuario;
import lombok.Data;

@Data
public class MatchUsuarioDTO {
    private Integer idMatch;
    private Usuario usuario;
    private Usuario usuarioMain;
}
