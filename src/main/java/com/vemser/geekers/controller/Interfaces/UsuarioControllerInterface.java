package com.vemser.geekers.controller.Interfaces;

import com.vemser.geekers.dto.PageDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.dto.UsuarioDesafioDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface UsuarioControllerInterface {

    @Operation(summary = "Lista o usuário por ID", description = "Realiza a pesquisa de usuário no banco pelo seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de usuário por ID realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    @GetMapping("/{id-usuario}")
    ResponseEntity<UsuarioDTO> listUsuarioPorId(@PathVariable(name = "id-usuario") Integer idUsuario) throws RegraDeNegocioException;

    @Operation(summary = "Lista o usuário por nome", description = "Realiza a pesquisa de usuário no banco pelo seu nome")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de usuário por nome realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    @GetMapping("/by-nome")
    ResponseEntity<List<UsuarioDTO>> ListarPorNome(@RequestParam("nome") String nome) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza os dados do usuário logado", description = "Realiza a atualização dos dados do usuário no banco pelo seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza de dados de usuário por id realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para atualizar os dados"),
                    @ApiResponse(responseCode = "500", description = "A atualização gerou uma exceção")
            }
    )
    @PutMapping("/{id-usuario}")
    ResponseEntity<UsuarioDTO> update(@Valid @RequestBody UsuarioDTO atualizarUsuario) throws RegraDeNegocioException;

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
    ResponseEntity<String> trocarSenha(@Valid @RequestParam("Nova Senha") String senha) throws RegraDeNegocioException;

}
