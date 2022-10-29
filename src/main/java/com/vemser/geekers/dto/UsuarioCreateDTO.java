package com.vemser.geekers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioCreateDTO {

    @NotEmpty
    @Schema(description = "Nome do usuário", example = "Felipe Noguez")
    private String nome;

    @NotEmpty
    @Schema(description = "E-mail do usuário", example = "felipenoguez@mail.com")
    private String email;

    @NotNull
    @Schema(description = "Telefone do usuário", example = "+555198778545")
    private String telefone;

    @NotEmpty
    @NotNull
    @Schema(description = "Senha do usuário", example = "K83nsdb&@s")
    private String senha;

    @Schema(description = "Data de nascimento do usuário", example = "2002-02-02")
    private LocalDate dataNascimento;

    @NotEmpty
    @Schema(description = "Genero do usuário", example = "M")
    private String sexo;

    private boolean logado;

}
