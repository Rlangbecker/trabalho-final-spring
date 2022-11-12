package com.vemser.geekers.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO extends UsuarioCreateDTO {

    @JsonIgnore
    private Integer idUsuario;

}
