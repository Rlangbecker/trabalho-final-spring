package com.vemser.geekers.dto;

import com.vemser.geekers.enums.TipoEvento;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EventoAtualDTO {
    @NotNull
    private TipoEvento tipoEvento;
}
