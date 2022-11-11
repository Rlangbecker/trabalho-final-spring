package com.vemser.geekers.controller.Interfaces;

import com.vemser.geekers.dto.*;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface UsuarioControllerInterface {

    @Operation(summary = "Listar usuários", description = "Lista os usuários ativos presentes no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Cadastro gerou uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<UsuarioDTO>> listUsuarios() throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Lista o usuário por ID", description = "Realiza a pesquisa de usuário no banco pelo seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de usuário por ID realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    @GetMapping("/{id-usuario}")
    ResponseEntity<UsuarioDTO> listUsuarioPorId(@PathVariable(name = "id-usuario") Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Lista o usuário por nome", description = "Realiza a pesquisa de usuário no banco pelo seu nome")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de usuário por nome realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    @GetMapping("/by-nome")
    ResponseEntity<List<UsuarioDTO>> ListarPorNome(@RequestParam("nome") String nome) throws BancoDeDadosException, RegraDeNegocioException;

    @Operation(summary = "Atualiza os dados do usuário por ID", description = "Realiza a atualização dos dados do usuário no banco pelo seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza de dados de usuário por id realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para atualizar os dados"),
                    @ApiResponse(responseCode = "500", description = "A atualização gerou uma exceção")
            }
    )
    @PutMapping("/{id-usuario}")
    ResponseEntity<UsuarioDTO> update(@PathVariable("id-usuario") Integer idUsuario,
                                      @Valid @RequestBody UsuarioDTO atualizarUsuario) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Remove os dados do usuário por ID", description = "Realiza a remoção dos dados do usuário no banco pelo seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Remoção de dados de usuário por id realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para remover os dados"),
                    @ApiResponse(responseCode = "500", description = "A remoção gerou uma exceção")
            }
    )
    @DeleteMapping("/{id-usuario}")
    ResponseEntity<Void> delete(@PathVariable("id-usuario") Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException;

    @Operation(summary = "Listar os usuários e matches", description = "Realiza a pesquisa de um usuário e lista os matches do mesmo presentes no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de matches do usuário realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    ResponseEntity<List<UsuarioMatchDTO>> listarUsuariosEMatchs(@RequestParam(required = false) Integer idPessoa);

    @Operation(summary = "Lista o usuário e o desafio do mesmo", description = "Realiza a pesquisa de um usuário e também do desafio do mesmo presente no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa do desafio do usuário realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    ResponseEntity<List<UsuarioDesafioDTO>> listarUsuarioEDesafios(@RequestParam(required = false) Integer idPessoa);

    @Operation(summary = "Listar usuários paginados", description = "Lista os usuários presentes no banco de dados com uma separação por páginas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listagem paginada realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Cadastro gerou uma exceção")
            }
    )
    ResponseEntity<PageDTO<UsuarioDTO>> listarUsuarioPaginado(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina);

    @Operation(summary = "Troque sua senha!", description = "Atualiza a senha da sua conta presente no banco de dados informando apenas o seu email e depois inserindo a nova senha.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualização da senha realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Cadastro gerou uma exceção")
            }
    )
    ResponseEntity<String> trocarSenha(@Valid @RequestParam("Email") String email, @Valid @RequestParam("Nova Senha") String senha) throws RegraDeNegocioException;
}
