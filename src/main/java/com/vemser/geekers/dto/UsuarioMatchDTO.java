package com.vemser.geekers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioMatchDTO {

    @NotEmpty
    private Integer idUsuario;
    @NotNull
    private String nome;
    @NotNull
    @Email
    private String email;
    @NotEmpty
    private Integer idMatch;
    @NotEmpty
    private Integer idUsuarioMatch;

}
