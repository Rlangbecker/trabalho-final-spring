package com.vemser.geekers.controller.Classes;

import com.vemser.geekers.controller.Interfaces.MatchControllerInterface;
import com.vemser.geekers.dto.MatchCreateDTO;
import com.vemser.geekers.dto.MatchDTO;
import com.vemser.geekers.dto.PageDTO;
import com.vemser.geekers.enums.TipoEvento;
import com.vemser.geekers.exception.RegraDeNegocioException;
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
public class MatchController implements MatchControllerInterface {
    private final MatchService matchService;

    @PostMapping("/{resposta}/usuario")
    public ResponseEntity<MatchDTO> create(@PathVariable("resposta") Integer resposta,
            @RequestParam("TipoEvento") TipoEvento tipoEvento,
            @Valid @RequestBody MatchCreateDTO matchCreateDTO) throws RegraDeNegocioException {
        log.info("Criando Match...");
        MatchDTO matchDTO = matchService.resolverDesafio(matchCreateDTO,resposta, tipoEvento);
        log.info("Match dado!");
        return new ResponseEntity<>(matchDTO, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<MatchDTO>> list() throws RegraDeNegocioException {
        List<MatchDTO> list = matchService.list();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/{idUser}")
    public ResponseEntity<List<MatchDTO>> listByIdUser(@PathVariable("idUser") Integer idUser) throws RegraDeNegocioException{
        return new ResponseEntity<>(matchService.listByUser(idUser), HttpStatus.OK);
    }

    @GetMapping("/match-paginado")
    public ResponseEntity<PageDTO<MatchDTO>> listarMatchesPaginados(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina){
        return new ResponseEntity<>(matchService.listMatchPaginada(paginaQueEuQuero, tamanhoDeRegistrosPorPagina), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        matchService.delete(id);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/UsuarioComMatch/{id}")
//    public ResponseEntity<UsuarioMatchDadosDTO> listaComUsuario(@PathVariable Integer id) throws RegraDeNegocioException{
//        return new ResponseEntity<>(matchService.listaComNomeUsuarioMatch(id),HttpStatus.OK);
//    }
}
