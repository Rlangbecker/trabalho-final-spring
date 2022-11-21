package com.vemser.geekers.entity;


import com.vemser.geekers.enums.TipoAtivo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

@Getter
@Setter
@Document(collection = "log_matchs")
public class LogMatchEntity {

    @Id
    private String id;
    private String data;
    private Integer idUsuario;
    private Integer usuarioMain;
    private TipoAtivo ativo;

}
