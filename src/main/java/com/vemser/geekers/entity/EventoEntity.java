package com.vemser.geekers.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vemser.geekers.enums.TipoEvento;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;

@Document(collection = "eventos")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventoEntity {

    @Id
    private String id;
    private TipoEvento evento;
    private String descricao;
    private LocalDate dataInicial;
    private LocalDate dataFim;
    private String status;
}
