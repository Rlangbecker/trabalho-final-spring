package com.vemser.geekers.dto;

import com.vemser.geekers.enums.TipoAtivo;
import lombok.Data;

@Data
public class LogDTO{

    private String id;
    private String data;
    private Integer idUsuario;
    private Integer usuarioMain;
    private TipoAtivo ativo;
}
