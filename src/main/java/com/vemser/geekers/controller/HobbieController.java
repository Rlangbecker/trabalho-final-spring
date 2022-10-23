package com.vemser.geekers.controller;

import com.vemser.geekers.entity.Hobbie;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.HobbieService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/hobbie") // localhost:8080/hobbie
@RequiredArgsConstructor
public class HobbieController {

    private final HobbieService hobbieService;

    @PostMapping("/{idHobbie}") // localhost:8080/hobbie/idHobbie
    public Hobbie create(@PathVariable("idHobbie") Integer idHobbie
            , @RequestBody Hobbie hobbie) {
        return hobbieService.create(hobbie);
    }

    @GetMapping // localhost:8080/hobbie
    public List<Hobbie> list() {
        return hobbieService.list();
    }


    @GetMapping("/{idHobbie}") // localhost:8080/hobbie/idHobbie
    public List<Hobbie> listByName(@PathVariable("idHobbie") Integer idHobbie) {
        return hobbieService.listByIdPessoa(idHobbie);
    }


    @PutMapping("/{idContato}") // localhost:8080/hobbie/idHobbie
    public Hobbie update(@PathVariable("idHobbie") Integer idHobbie,
                         @RequestBody Hobbie hobbieAtualizar) throws RegraDeNegocioException {
        return hobbieService.update(idHobbie, hobbieAtualizar);
    }

    @DeleteMapping("/{idHobbie}") // localhost:8080/hobbie/idHobbie
    public void delete(@PathVariable("idHobbie") Integer idHobbie) throws RegraDeNegocioException {
        hobbieService.delete(idHobbie);
    }


}
