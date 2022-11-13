package com.vemser.geekers.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vemser.geekers.enums.TipoAtivo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MatchCreateDTO {
    @NotNull
    private Integer idUsuario;

    @JsonIgnore
    private Integer idUsuarioMatch;
    @JsonIgnore
    private TipoAtivo ativo;
}
