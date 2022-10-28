package com.vemser.geekers.dto;

import com.vemser.geekers.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComentarioCreateDTO {


    @NotBlank(message = "Comentário não pode ser inserido em branco.")
    @NotNull(message = "Comentário não pode ser nulo.")
    @Size(max = 225)
    @Schema(description = "Comentário do usuário", example = "Demais!!")
    private String comentario;

    private Usuario usuario;

}
