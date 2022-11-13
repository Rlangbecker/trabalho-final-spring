package com.vemser.geekers.controller.Classes;

import com.vemser.geekers.controller.Interfaces.DesafioControllerInterface;
import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.DesafioDTO;
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
public class DesafioController implements DesafioControllerInterface {

    private final DesafioService desafioService;

    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }

    @PostMapping// localhost:8080/desafio
    public ResponseEntity<DesafioDTO> create(
            @Valid @RequestBody DesafioCreateDTO desafio) throws RegraDeNegocioException {
        DesafioDTO desafioDTO = desafioService.create(desafio);
        return new ResponseEntity<>(desafioDTO, HttpStatus.OK);
    }

    @GetMapping("/{idDesafio}")
    public ResponseEntity<DesafioDTO> listByIdDesafio(@PathVariable("idDesafio") Integer idDesafio) throws RegraDeNegocioException {
        return new ResponseEntity<>(desafioService.findById(idDesafio), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DesafioDTO>> list() throws RegraDeNegocioException {
        List<DesafioDTO> list = desafioService.list();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUser}")
    public ResponseEntity<DesafioDTO> listByIdUser(@PathVariable("idUser") Integer idUser) throws RegraDeNegocioException{
        return new ResponseEntity<>(desafioService.findByUsuario(idUser), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<DesafioDTO> update(
                                         @Valid @RequestBody DesafioCreateDTO desafioAtualizar) throws RegraDeNegocioException {
        return new ResponseEntity<>(desafioService.edit(desafioAtualizar), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete() throws RegraDeNegocioException {
        desafioService.delete();
        return ResponseEntity.ok().build();
    }

}
