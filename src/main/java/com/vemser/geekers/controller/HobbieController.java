package com.vemser.geekers.controller;

import com.vemser.geekers.entity.Hobbie;
import com.vemser.geekers.service.HobbieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/contato") // localhost:8080/contato
public class HobbieController {


        private HobbieService hobbieService;

        public HobbieController(HobbieService hobbieService) {
            this.hobbieService=hobbieService;
        }

        @PostMapping("/{idPessoa}") // localhost:8080/contato/idPessoa
        public Hobbie create(@PathVariable("idPessoa") Integer id
                ,@RequestBody Hobbie hobbie) {
            return hobbieService.create(hobbie);
        }

        @GetMapping // localhost:8080/contato
        public List<Hobbie> list() {
            return hobbieService.list();
        }


        @GetMapping("/{idPessoa}") // localhost:8080/contato/idPessoa
        public List<Hobbie> listByName(@PathVariable("idPessoa") Integer idPessoa) {
            return hobbieService.listByIdPessoa(idPessoa);
        }


        @PutMapping("/{idContato}") // localhost:8080/contato/idContato
        public Hobbie update(@PathVariable("idContato") Integer id,
                             @RequestBody Hobbie hobbieAtualizar) throws Exception {
            return hobbieService.update(id, hobbieAtualizar);
        }

        @DeleteMapping("/{idContato}") // localhost:8080/contato/idContato
        public void delete(@PathVariable("idContato") Integer id) throws Exception {
            hobbieService.delete(id);
        }


    }
