package com.vemser.geekers.controller;

import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.entity.Desafio;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.DesafioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/desafio")
@Slf4j
public class DesafioController {

    private DesafioService desafioService;

    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }

    @PostMapping("/{idUsuario}")// localhost:8080/pessoa
    public ResponseEntity<DesafioDTO> create(@PathVariable("idUsuario") Integer id,
            @Valid @RequestBody DesafioCreateDTO desafio) throws RegraDeNegocioException {
        log.info("Criando Desafio...");
        DesafioDTO desafioDTO = desafioService.create(desafio, id);
        log.info("Desafio criado!");
        return new ResponseEntity<>(desafioDTO, HttpStatus.OK);
    }

    @GetMapping // localhost:8080/pessoa
    public ResponseEntity<List<DesafioDTO>> list() throws BancoDeDadosException {
        return new ResponseEntity<>(desafioService.list(), HttpStatus.OK);
    }
    @PutMapping("/{id}") // localhost:8080/pessoa/1000
    public ResponseEntity<DesafioDTO> update(@PathVariable("id") Integer id,
                                         @Valid @RequestBody DesafioCreateDTO desafioAtualizar) throws Exception {
        return new ResponseEntity<>(desafioService.update(id, desafioAtualizar), HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // localhost:8080/pessoa/10
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        desafioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
