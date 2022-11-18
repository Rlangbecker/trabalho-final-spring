package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.EventoAtualDTO;
import com.vemser.geekers.dto.EventoCreateDTO;
import com.vemser.geekers.dto.EventoDTO;
import com.vemser.geekers.dto.EventoDTOContador;
import com.vemser.geekers.entity.EventoEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.enums.TipoEvento;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final ObjectMapper objectMapper;


    public EventoDTO create(EventoCreateDTO evento, TipoEvento tipoEvento ,LocalDate inical, LocalDate dataFinal) throws RegraDeNegocioException {
        EventoEntity eventoEntity = objectMapper.convertValue(evento, EventoEntity.class);
        eventoEntity.setEvento(tipoEvento);
        eventoEntity.setDataInicial(inical);
        eventoEntity.setDataFim(dataFinal);
        eventoEntity.setStatus(TipoAtivo.ATIVO.name());
        eventoRepository.save(eventoEntity);
        EventoDTO eventoDTO = objectMapper.convertValue(eventoEntity, EventoDTO.class);

        return eventoDTO;
    }

    public EventoDTO findEvento(TipoEvento tipoEvento){
        return eventoRepository.findByEvento(tipoEvento);
    }

    public void delete(String id){
        eventoRepository.deleteById(id);
    }

    public EventoDTO findById(String id) throws RegraDeNegocioException {
        EventoEntity eventoEntity = eventoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Evento não encontrado"));
        return objectMapper.convertValue(eventoEntity, EventoDTO.class);
    }

    public EventoDTO desativar(String id) throws RegraDeNegocioException {
        EventoEntity evento = objectMapper.convertValue(findById(id), EventoEntity.class);
        evento.setStatus(TipoAtivo.INATIVO.name());
        return objectMapper.convertValue(evento, EventoDTO.class);

    }

    public EventoDTOContador countByEventos(TipoEvento tipoEvento){
        EventoDTOContador evento = new EventoDTOContador();
        evento.setQuantidade(eventoRepository.countByEvento(tipoEvento));
        evento.setTipoEvento(tipoEvento);
        return evento;
    }

    public Boolean existeEvento(TipoEvento tipoEvento){
        LocalDate local = LocalDate.now();
        EventoDTO eventoDTO = findEvento(tipoEvento);
        EventoEntity eventoEntity = objectMapper.convertValue(eventoDTO, EventoEntity.class);
        int result1 = local.compareTo(eventoEntity.getDataInicial());
        int result2 = local.compareTo(eventoEntity.getDataFim());
        if(result1 == 0 || result1 > 0 && result2 == 0 || result2 < 0 ){
            return true;
        }
        else {
            return false;
        }
    }

}
