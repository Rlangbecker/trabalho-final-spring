package com.vemser.geekers.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ComentarioCreateDTO {


    @NotBlank(message = "Comentário não pode ser inserido em branco.")
    @NotNull(message = "Comentário não pode ser nulo.")
    @Size(max = 225)
    private String comentario;
//    private Usuario usuario;

}
