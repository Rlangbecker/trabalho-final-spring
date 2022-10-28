package com.vemser.geekers.controller;

import com.vemser.geekers.dto.*;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.DesafioService;
import com.vemser.geekers.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/match")
@Slf4j
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @PostMapping("/{resposta}")// localhost:8080/desafio
    public ResponseEntity<MatchDTO> create(@PathVariable("resposta") Integer resposta,
            @Valid @RequestBody MatchCreateDTO matchCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando Match...");
        MatchDTO matchDTO = matchService.resolverDesafio(matchCreateDTO, resposta);
        log.info("Match dado!");
        return new ResponseEntity<>(matchDTO, HttpStatus.OK);
    }

    @GetMapping // localhost:8080/pessoa
    public ResponseEntity<List<MatchDTO>> list() throws BancoDeDadosException {
        List<MatchDTO> list = matchService.list();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<List<MatchDTO>> listByIdUser(@PathVariable("idUser") Integer idUser) throws Exception{
        return new ResponseEntity<>(matchService.listByUser(idUser), HttpStatus.OK);
    }
    @PutMapping("/{id}") // localhost:8080/pessoa/1000
    public ResponseEntity<MatchDTO> update(@PathVariable("id") Integer id,
                                             @Valid @RequestBody MatchCreateDTO matchCreateDTO) throws Exception {
        return new ResponseEntity<>(matchService.update(id, matchCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // localhost:8080/pessoa/10
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        matchService.delete(id);
        return ResponseEntity.ok().build();
    }
}
