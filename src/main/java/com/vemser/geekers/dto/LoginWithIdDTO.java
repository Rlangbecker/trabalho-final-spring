package com.vemser.geekers.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginWithIdDTO {
    @NotNull
    private Integer idUsuario;

    @NotNull
    @Schema(example = "user")
    private String login;

    @NotNull
    @Schema(example = "123")
    @JsonIgnore
    private String senha;
}
