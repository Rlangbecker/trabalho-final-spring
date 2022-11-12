package com.vemser.geekers.controller.Interfaces;

import com.vemser.geekers.dto.HobbieCreateDTO;
import com.vemser.geekers.dto.HobbieDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface HobbieControllerInterface {
    @Operation(summary = "Criar hobbies", description = "Cria um hobbie com o ID do usuario passado no body")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria um hobbie para o Usuario informado no body"),
                    @ApiResponse(responseCode = "400", description = "Erro na passagem de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping // localhost:8080/hobbie/idUsuario
    public ResponseEntity<HobbieDTO> create(@Valid @RequestBody HobbieDTO hobbieDTO) throws RegraDeNegocioException;

    @Operation(summary = "listar hobbies", description = "Lista todos os hobbie do usuario passado por ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de hobbies do Usuario"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("{idUsuario}") // localhost:8080/hobbie/idUsuario
    public HobbieDTO listByIdUsuario(@Valid @PathVariable ("idUsuario") Integer idUsuario) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar hobbie", description = "Atualiza o hobbie do usuario passado por ID(Hobbie)")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza o hobbie do Usuario por ID do Hobbie"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping // localhost:8080/hobbie/idHobbie
    public ResponseEntity<HobbieDTO> update(@Valid @RequestBody HobbieCreateDTO hobbieAtualizar) throws RegraDeNegocioException;
    @Operation(summary = "Deletar hobbie", description = "Deleta o hobbie do usuario passado por ID(Hobbie)")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta o hobbie do Usuario por ID do Hobbie"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping() // localhost:8080/hobbie/idHobbie
    public void delete() throws RegraDeNegocioException;


}
