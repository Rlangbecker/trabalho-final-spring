package com.vemser.geekers.dto;

import com.vemser.geekers.entity.Usuario;

import javax.validation.constraints.NotBlank;

public class HobbieCreateDTO {

    @NotBlank
    private String descricao;
    @NotBlank
    private String tipoHobbie;
}
