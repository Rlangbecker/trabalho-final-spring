package com.vemser.geekers.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class HobbieCreateDTO {

    @NotNull
    @Schema (description = "idUsuario" , example="5")
    private Integer idUsuario;

    @NotBlank
    @Schema(description = "Descricao do hobbie" , example ="Call of duty")
    private String descricao;


    @NotBlank
    @Schema (description = "1" , example = "1")
    private String tipoHobbie;
}
