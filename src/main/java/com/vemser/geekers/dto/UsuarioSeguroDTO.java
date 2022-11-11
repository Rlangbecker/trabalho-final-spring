package com.vemser.geekers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class UsuarioSeguroDTO {

    private String nome;
    private String email;
    private String token;
}
