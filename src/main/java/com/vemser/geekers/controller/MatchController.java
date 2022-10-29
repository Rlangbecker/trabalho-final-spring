package com.vemser.geekers.controller;

import com.vemser.geekers.dto.MatchCreateDTO;
import com.vemser.geekers.dto.MatchDTO;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Dar Match", description = "Resolve o desafio do usuario e dar um match")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o match criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{resposta}")// localhost:8080/desafio
    public ResponseEntity<MatchDTO> create(@PathVariable("resposta") Integer resposta,
            @Valid @RequestBody MatchCreateDTO matchCreateDTO) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Criando Match...");
        MatchDTO matchDTO = matchService.resolverDesafio(matchCreateDTO, resposta);
        log.info("Match dado!");
        return new ResponseEntity<>(matchDTO, HttpStatus.OK);
    }

    @Operation(summary = "Listar matchs", description = "Listar matchs do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os matchs"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping // localhost:8080/pessoa
    public ResponseEntity<List<MatchDTO>> list() throws BancoDeDadosException {
        List<MatchDTO> list = matchService.list();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//    @Operation(summary = "Listar matchs por id do usuario", description = "Listar matchs por id do usuario do banco")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Retorna os matchs"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/{idUser}")
//    public ResponseEntity<List<MatchDTO>> listByIdUser(@PathVariable("idUser") Integer idUser) throws Exception{
//        return new ResponseEntity<>(matchService.listByUser(idUser), HttpStatus.OK);
//    }

    @Operation(summary = "Atualiza match por id", description = "Atualiza um match por id no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna match atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}") // localhost:8080/pessoa/1000
    public ResponseEntity<MatchDTO> update(@PathVariable("id") Integer id,
                                             @Valid @RequestBody MatchCreateDTO matchCreateDTO) throws Exception {
        return new ResponseEntity<>(matchService.update(id, matchCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deleta match por id", description = "Deleta um match por id no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna endereco deletada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}") // localhost:8080/pessoa/10
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        matchService.delete(id);
        return ResponseEntity.ok().build();
    }
}
