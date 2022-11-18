package com.vemser.geekers.dto;

import com.vemser.geekers.enums.TipoEvento;
import lombok.Data;

import java.time.LocalDate;


@Data
public class EventoDTO extends EventoCreateDTO {

    private String id;
    private TipoEvento evento;
    private LocalDate dataInicial;
    private LocalDate dataFim;
    private String status;
}
