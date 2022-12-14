package com.vemser.geekers.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginWithIdDTO {
    @NotNull
    private Integer idUsuario;

    @NotBlank
    private String nome;

    @NotNull
    @NotBlank
    @Schema(example = "user")
    private String login;

    @JsonIgnore
    @NotNull
    @Schema(example = "123")
    private String senha;

    @NotNull
    @NotBlank
    @Email
    private String email;
}
