package com.vemser.geekers.dto;

import com.vemser.geekers.entity.Usuario;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DesafioCreateDTO {
    @NotBlank
    private String pergunta;
    @NotBlank
    @Size(max = 1)
    private String resposta;
    private Usuario Usuario;

}
