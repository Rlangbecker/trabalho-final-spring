package com.vemser.geekers.controller;

import com.vemser.geekers.entity.Desafio;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.service.DesafioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/desafio")
public class DesafioController {

    private DesafioService desafioService;

    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }

    @PostMapping // localhost:8080/pessoa
    public ResponseEntity<Desafio> create(@Valid @RequestBody Desafio desafio) throws Exception {
        return new ResponseEntity<>(desafioService.create(desafio), HttpStatus.OK);
    }

    @GetMapping // localhost:8080/pessoa
    public List<Desafio> list() throws BancoDeDadosException {
        return desafioService.list();
    }
    @PutMapping("/{id}") // localhost:8080/pessoa/1000
    public ResponseEntity<Desafio> update(@PathVariable("id") Integer id,
                                         @Valid @RequestBody Desafio desafioAtualizar) throws Exception {
        return new ResponseEntity<>(desafioService.update(id, desafioAtualizar), HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // localhost:8080/pessoa/10
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        desafioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
