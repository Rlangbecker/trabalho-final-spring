package com.vemser.geekers.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MatchCreateDTO {
    @NotNull
    private Integer idUsuario;
    @NotNull
    private Integer usuarioMain;
}
