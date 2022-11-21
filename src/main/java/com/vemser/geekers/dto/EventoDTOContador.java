package com.vemser.geekers.dto;

import com.vemser.geekers.enums.TipoEvento;
import lombok.Data;

@Data
public class EventoDTOContador {
    private TipoEvento tipoEvento;
    private Integer quantidade;
}
