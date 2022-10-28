package com.vemser.geekers.dto;

import com.vemser.geekers.entity.Usuario;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DesafioUsuarioDTO {
    private Integer idDesafio;
    @NotBlank
    private String pergunta;
    private Integer Usuario;
}
