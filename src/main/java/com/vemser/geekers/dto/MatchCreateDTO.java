package com.vemser.geekers.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MatchCreateDTO {
    @NotNull
    private Integer usuario;
    @NotNull
    private Integer usuarioMain;
}
