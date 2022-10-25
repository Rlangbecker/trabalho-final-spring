package com.vemser.geekers.dto;

import com.vemser.geekers.entity.Usuario;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HobbieCreateDTO {

    @NotBlank
    private String descricao;
    @NotBlank
    private String tipoHobbie;
}
