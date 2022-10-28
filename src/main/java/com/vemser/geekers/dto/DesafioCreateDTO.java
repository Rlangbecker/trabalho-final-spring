package com.vemser.geekers.dto;

import com.vemser.geekers.entity.Usuario;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DesafioCreateDTO {
    @NotBlank
    private String pergunta;
    @NotNull
    private Integer resposta;

    private Usuario Usuario;

}
