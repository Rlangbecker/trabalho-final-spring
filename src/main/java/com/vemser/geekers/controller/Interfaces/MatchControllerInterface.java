package com.vemser.geekers.controller.Interfaces;

import com.vemser.geekers.dto.MatchCreateDTO;
import com.vemser.geekers.dto.MatchDTO;
import com.vemser.geekers.dto.PageDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface MatchControllerInterface {
    @Operation(summary = "Dar Match", description = "Resolve o desafio do usuario e dar um match")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o match criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{resposta}")
    public ResponseEntity<MatchDTO> create(@PathVariable("resposta") Integer resposta,
                                           @Valid @RequestBody MatchCreateDTO matchCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Listar todos os matchs - (GOLD)", description = "Listar matchs do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os matchs"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<MatchDTO>> list() throws RegraDeNegocioException;

    @Operation(summary = "Listar matchs por id do usuario", description = "Listar matchs por id do usuario do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os matchs"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idUser}")
    public ResponseEntity<List<MatchDTO>> listByIdUser(@PathVariable("idUser") Integer idUser) throws RegraDeNegocioException;

    @Operation(summary = "Deleta match por id", description = "Deleta um match por id no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna endereco deletada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Listar matches paginados", description = "Lista os matches presentes no banco de dados com uma separação por páginas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listagem paginada realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Cadastro gerou uma exceção")
            }
    )
    ResponseEntity<PageDTO<MatchDTO>> listarMatchesPaginados(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina);

}
