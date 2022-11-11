package com.vemser.geekers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SenhaNovaDTO {
    @NotBlank
    private String novaSenha;
}
