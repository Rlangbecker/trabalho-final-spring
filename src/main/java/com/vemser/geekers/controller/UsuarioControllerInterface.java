package com.vemser.geekers.controller;

import com.vemser.geekers.dto.UsuarioCreateDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface UsuarioControllerInterface {

    @Operation(summary = "Cadastro de usuário", description = "Realiza o cadastro do usuário no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de usuário realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Cadastro gerou uma exceção")
            }
    )
    @PostMapping("/{usuario}")
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuarioDto) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Lista de usuários", description = "Realiza a pesquisa de usuários no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de usuários realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listUsuarios() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Lista o usuário por ID", description = "Realiza a pesquisa de usuário no banco pelo seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de usuário por ID realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    @GetMapping("/{id-usuario}")
    public ResponseEntity<UsuarioDTO> listUsuarioPorId(@PathVariable(name = "id-usuario") Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Lista o usuário por nome", description = "Realiza a pesquisa de usuário no banco pelo seu nome")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de usuário por nome realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    @GetMapping("/nome")
    public ResponseEntity<UsuarioDTO> ListarPorNome(@RequestParam("nome") String nome) throws BancoDeDadosException, RegraDeNegocioException;

    @Operation(summary = "Lista o usuário por nome", description = "Realiza a pesquisa de usuário no banco pelo seu nome")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de usuário por nome realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    @PutMapping("/{id-usuario}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable("id-usuario") Integer idUsuario,
                                             @Valid @RequestBody UsuarioDTO atualizarUsuario) throws RegraDeNegocioException, BancoDeDadosException;

}
