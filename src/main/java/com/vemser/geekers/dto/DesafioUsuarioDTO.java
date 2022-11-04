package com.vemser.geekers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DesafioUsuarioDTO {
    private Integer idDesafio;
    @NotBlank
    private String pergunta;
    private Integer Usuario;
}
