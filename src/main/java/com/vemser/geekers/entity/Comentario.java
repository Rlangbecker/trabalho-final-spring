package com.vemser.geekers.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comentario {

    private Integer idComentario;
    private String comentario;
    private Usuario usuario;

}
