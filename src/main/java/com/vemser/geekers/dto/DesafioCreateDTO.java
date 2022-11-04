package com.vemser.geekers.dto;

import com.vemser.geekers.entity.UsuarioEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DesafioCreateDTO {
    @NotBlank
    private String pergunta;
    @NotNull
    private Integer resposta;

    private Integer idUsuario;

}
