package com.vemser.geekers.dto;

import com.vemser.geekers.entity.Usuario;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MatchCreateDTO {
    @NotNull
    private Integer usuario;
    @NotNull
    private Integer usuarioMain;
}
