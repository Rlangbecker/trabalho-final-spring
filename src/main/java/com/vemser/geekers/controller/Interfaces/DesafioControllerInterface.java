package com.vemser.geekers.controller.Interfaces;

import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface DesafioControllerInterface {
    @Operation(summary = "Criar Desafio", description = "Criar um desafio para um usuário específico presente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um desafio criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<DesafioDTO> create(
                                             @Valid @RequestBody DesafioCreateDTO desafio) throws RegraDeNegocioException;

    @Operation(summary = "Listar Desafio por id", description = "Listar Desafio por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um desafio por id"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idDesafio}")
    public ResponseEntity<DesafioDTO> listByIdDesafio(@PathVariable("idDesafio") Integer idDesafio) throws RegraDeNegocioException;

    @Operation(summary = "Listar todos os desafios", description = "Listar todos os desafios")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna todos os desafios"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<DesafioDTO>> list() throws RegraDeNegocioException;

    @Operation(summary = "Listar desafio por usuario", description = "Listar desafio por usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um desafio por usuario"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario/{idUser}")
    public ResponseEntity<DesafioDTO> listByIdUser(@PathVariable("idUser") Integer idUser) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar desafio por id", description = "Atualizar desafio por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um desafio atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    public ResponseEntity<DesafioDTO> update(@Valid @RequestBody DesafioCreateDTO desafioAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Deletar desafio por id", description = "Deletar desafio por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna um desafio deletado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
     ResponseEntity<Void> delete() throws RegraDeNegocioException;

    }
