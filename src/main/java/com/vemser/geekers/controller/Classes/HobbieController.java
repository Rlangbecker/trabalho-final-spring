package com.vemser.geekers.controller.Classes;

import com.vemser.geekers.controller.Interfaces.HobbieControllerInterface;
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

import javax.validation.Valid;


@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/hobbie") // localhost:8080/hobbie
public class HobbieController implements HobbieControllerInterface {

    private final HobbieService hobbieService;

    @PostMapping // localhost:8080/hobbie/idUsuario
    public ResponseEntity<HobbieDTO> create(@Valid @RequestBody HobbieDTO hobbieDTO) throws RegraDeNegocioException{
        HobbieDTO hDTO= hobbieService.create(hobbieDTO);
        return new ResponseEntity<>(hDTO, HttpStatus.OK) ;
    }
    @GetMapping("{idUsuario}") // localhost:8080/hobbie/idUsuario
    public HobbieDTO listByIdUsuario(@Valid @PathVariable ("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        log.info("Listando Hobbies | idUsuario: " + idUsuario);
        return hobbieService.findByIdUsuario(idUsuario);
    }
    @PutMapping // localhost:8080/hobbie/idHobbie
    public ResponseEntity<HobbieDTO> update(@Valid @RequestBody HobbieCreateDTO hobbieAtualizar) throws RegraDeNegocioException {
        HobbieDTO hobbieDTO = hobbieService.editar(hobbieAtualizar);
        //VERIFICAR SE VAI FICAR POR IDHOBBIE OU IDUSUARIO
        return new ResponseEntity<>(hobbieDTO, HttpStatus.OK);
    }
    @DeleteMapping // localhost:8080/hobbie/idHobbie
    public void delete() throws RegraDeNegocioException {
        hobbieService.remover();
    }
}
