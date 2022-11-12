package com.vemser.geekers.dto;


import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MatchUsuarioDTO {
    @NotEmpty
    private Integer idMatch;
    @NotNull
    private UsuarioEntity usuarioEntity;
    @NotNull
    private UsuarioEntity usuarioEntityMain;
    @NotNull
    private TipoAtivo ativo;
}
