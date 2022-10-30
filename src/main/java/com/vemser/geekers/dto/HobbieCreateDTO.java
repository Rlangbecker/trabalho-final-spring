package com.vemser.geekers.dto;

import com.vemser.geekers.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HobbieCreateDTO {

    @NotBlank
    @Schema (description = "idUsuario" , example="5")
    private Integer idUsuario;

    @NotBlank
    @Schema(description = "Descricao do hobbie" , example ="Call of duty")
    private String descricao;

    @NotBlank
    @Schema (description = "1" , example = "Jogos")
    private String tipoHobbie;
}
