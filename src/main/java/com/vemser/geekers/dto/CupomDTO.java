package com.vemser.geekers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CupomDTO {
    @NotBlank
    private String nome;
    @NotNull
    private Double preco;
    @NotNull
    private LocalDate DataVencimento;
}
