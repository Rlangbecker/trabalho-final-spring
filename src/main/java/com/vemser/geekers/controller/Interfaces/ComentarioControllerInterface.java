package com.vemser.geekers.controller.Interfaces;

import com.vemser.geekers.dto.ComentarioCreateDTO;
import com.vemser.geekers.dto.ComentarioDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ComentarioControllerInterface {

    @Operation(summary = "Adiciona comentário", description = "Adiciona comentário no perfil de usuário no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de comentário realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para adicionar comentário"),
                    @ApiResponse(responseCode = "500", description = "Comentário gerou uma exceção")
            }
    )
    @PostMapping("/{idUsuario}")
    public ResponseEntity<ComentarioDTO> create(@Valid @PathVariable("idUsuario") Integer idUsuario, @RequestBody ComentarioCreateDTO comentario) throws RegraDeNegocioException;

    @Operation(summary = "Lista de comentários do usuário por ID do usuário", description = "Realiza a pesquisa de comentários pelo ID do usuário no banco ")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de comentários por ID realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar comentário"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa de comentário gerou uma exceção")
            }
    )
    @GetMapping("/{id-usuario}/usuario")
    public ResponseEntity<List<ComentarioDTO>> listaComentarioPorIdUsuario(@PathVariable("id-usuario") Integer idComentario) throws RegraDeNegocioException;

    @Operation(summary = "procura um comentário pelo id", description = "Realiza a pesquisa de um comentário no banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa do comentário realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar comentário"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa de comentário gerou uma exceção")
            }
    )
    @GetMapping("/{id-comentario}/comentario")
    public ResponseEntity<ComentarioDTO> findByIdComentario(@PathVariable(name = "id-comentario") Integer idComentario) throws RegraDeNegocioException;

    @Operation(summary = "Atualiza o comentário do usuário por ID", description = "Realiza a atualização do comentário do usuário no banco pelo seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza comentário de usuário por id realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para atualizar comentário"),
                    @ApiResponse(responseCode = "500", description = "A atualização de comentário gerou uma exceção")
            }
    )
    @PutMapping("/{id-comentario}")
    public ResponseEntity<ComentarioDTO> update(@PathVariable("id-comentario") Integer idComentario,
                                                @Valid @RequestBody ComentarioDTO atualizarComantario) throws RegraDeNegocioException;

    @Operation(summary = "Remove o comentário do usuário por ID", description = "Realiza a remoção dos comentário do usuário no banco pelo seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Remoção de comentário de usuário por id realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para remover o comentário"),
                    @ApiResponse(responseCode = "500", description = "A remoção de comentário gerou uma exceção")
            }
    )
    @DeleteMapping("/{id-comentario}/remover-comentario")
    public ResponseEntity<Void> delete(@PathVariable("id-comentario") Integer idUsuario) throws RegraDeNegocioException;

}
