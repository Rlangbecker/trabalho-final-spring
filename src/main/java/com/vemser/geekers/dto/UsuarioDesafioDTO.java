package com.vemser.geekers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDesafioDTO {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String pergunta;
    private Integer resposta;
    private Integer idDesafio;
}
