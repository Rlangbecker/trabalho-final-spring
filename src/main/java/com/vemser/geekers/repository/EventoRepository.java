package com.vemser.geekers.repository;

import com.vemser.geekers.dto.EventoAtualDTO;
import com.vemser.geekers.dto.EventoDTO;
import com.vemser.geekers.entity.EventoEntity;
import com.vemser.geekers.enums.TipoEvento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends MongoRepository<EventoEntity, String> {

    EventoDTO findByEvento(TipoEvento tipoEvento);
    Integer countByEvento(TipoEvento evento);
}
