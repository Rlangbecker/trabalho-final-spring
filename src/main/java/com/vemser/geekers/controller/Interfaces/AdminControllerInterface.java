package com.vemser.geekers.controller.Interfaces;

import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.dto.UsuarioMatchDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface AdminControllerInterface {

    @Operation(summary = "Listar usuários", description = "Lista os usuários ativos presentes no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Cadastro gerou uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<UsuarioDTO>> listUsuarios() throws RegraDeNegocioException;

    @Operation(summary = "Listar os usuários e matches", description = "Realiza a pesquisa de um usuário e lista os matches do mesmo presentes no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de matches do usuário realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    ResponseEntity<List<UsuarioMatchDTO>> listarUsuariosEMatchs(@RequestParam(required = false) Integer idPessoa);

    @Operation(summary = "Listar usuários inativos", description = "Realiza a pesquisa de usuário inativos no banco pelo seu nome")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pesquisa de usuários inativos realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para pesquisar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "A pesquisa gerou uma exceção")
            }
    )
    @GetMapping("/inativos")
    ResponseEntity<List<UsuarioDTO>> listUsuariosInativos();

    @Operation(summary = "Atualize o cargo do usuário!", description = "Atualiza o cargo do usuário mencionado buscando o mesmo pelo ID presente no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualização de cargo realizado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Cadastro gerou uma exceção")
            }
    )
    ResponseEntity<String> alterarCargo(@Valid @PathVariable("idUsuario") Integer id) throws RegraDeNegocioException;


    @Operation(summary = "Remove os dados do usuário por ID", description = "Realiza a remoção dos dados do usuário no banco pelo seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Remoção de dados de usuário por id realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para remover os dados"),
                    @ApiResponse(responseCode = "500", description = "A remoção gerou uma exceção")
            }
    )
    @DeleteMapping("/{id-usuario}")
    public ResponseEntity<Void> delete(@PathVariable("id-usuario") Integer idUsuario) throws RegraDeNegocioException;

}
