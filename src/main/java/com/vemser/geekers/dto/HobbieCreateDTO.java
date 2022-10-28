package com.vemser.geekers.dto;

import com.vemser.geekers.entity.Usuario;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HobbieCreateDTO {
    
    @NotBlank
    private Integer idUsuario;
    @NotBlank
    private String descricao;
    @NotBlank
    private String tipoHobbie;
}
