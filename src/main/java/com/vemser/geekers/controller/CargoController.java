package com.vemser.geekers.controller;

import com.vemser.geekers.dto.CargoCreateDTO;
import com.vemser.geekers.dto.CargoDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cargos")
public class CargoController {
    private final CargoService cargoService;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<CargoDTO> create(@PathVariable ("idUsuario") Integer idUsuario,
                                           @Valid @RequestBody CargoCreateDTO cargoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(cargoService.create(idUsuario, cargoCreateDTO), HttpStatus.OK);
    }
    
}
