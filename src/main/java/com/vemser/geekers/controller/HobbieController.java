package com.vemser.geekers.controller;

import com.vemser.geekers.dto.HobbieCreateDTO;
import com.vemser.geekers.dto.HobbieDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.HobbieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/hobbie") // localhost:8080/hobbie
public class HobbieController implements HobbieControllerInterface{

    private final HobbieService hobbieService;

    @PostMapping("/{idUsuario}") // localhost:8080/hobbie/idUsuario
    public ResponseEntity<HobbieDTO> create(@PathVariable("idUsuario") Integer idUsuario , @RequestBody HobbieDTO hobbieDTO) throws RegraDeNegocioException{
        HobbieDTO hDTO= hobbieService.create(idUsuario,hobbieDTO);
        return new ResponseEntity<>(hDTO, HttpStatus.OK) ;
    }
    @GetMapping("{idUsuario}") // localhost:8080/hobbie/idUsuario
    public HobbieDTO listByIdUsuario(@PathVariable ("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        log.info("Listando Hobbies | idUsuario: " + idUsuario);
        return hobbieService.listByIdUsuario(idUsuario);
    }
    @PutMapping("/{idHobbie}") // localhost:8080/hobbie/idHobbie
    public ResponseEntity<HobbieDTO> update(@PathVariable("idHobbie") Integer idHobbie,
                         @RequestBody HobbieCreateDTO hobbieAtualizar) throws RegraDeNegocioException {
        HobbieDTO hobbieDTO = hobbieService.editar(idHobbie,hobbieAtualizar);
        //VERIFICAR SE VAI FICAR POR IDHOBBIE OU IDUSUARIO
        return new ResponseEntity<>(hobbieDTO, HttpStatus.OK);
    }
    @DeleteMapping("/{idHobbie}") // localhost:8080/hobbie/idHobbie
    public void delete(@PathVariable("idHobbie") Integer idHobbie) throws RegraDeNegocioException {
        hobbieService.remover(idHobbie);
    }
}
