package com.vemser.geekers.controller.Classes;

import com.vemser.geekers.controller.Interfaces.EventoControllerInterface;
import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.EventoCreateDTO;
import com.vemser.geekers.dto.EventoDTO;
import com.vemser.geekers.dto.EventoDTOContador;
import com.vemser.geekers.enums.TipoEvento;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.EventoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/evento")
@Validated
public class EventoController implements EventoControllerInterface {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoDTO> create(@Valid @RequestParam("Data inicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
                                            @Valid @RequestParam("Data final") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
                                            @Valid @RequestParam("TipoEvento") TipoEvento tipoEvento,
                                            @Valid @RequestBody EventoCreateDTO evento) throws RegraDeNegocioException {

        return new ResponseEntity<>(eventoService.create(evento, tipoEvento, dataInicial, dataFinal), HttpStatus.OK);
    }

    @GetMapping("/count-by-eventos")
    public ResponseEntity<EventoDTOContador> countByEventos(@Valid @RequestParam("TipoEvento") TipoEvento tipoEvento){
        return new ResponseEntity<>(eventoService.countByEventos(tipoEvento), HttpStatus.OK);
    }

    @GetMapping("/find_by_eventos")
    public ResponseEntity<EventoDTO> findByEventos(@Valid @RequestParam("TipoEvento") TipoEvento tipoEvento){
        return new ResponseEntity<>(eventoService.findEvento(tipoEvento), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) throws RegraDeNegocioException {
        eventoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> updateStatus(@PathVariable("id") String id) throws  RegraDeNegocioException {
        eventoService.desativar(id);
        return new ResponseEntity<>(eventoService.desativar(id), HttpStatus.OK);
    }



}
