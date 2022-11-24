package com.vemser.geekers.controller.Interfaces;

import com.vemser.geekers.dto.LoginDTO;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.dto.UsuarioCreateDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface AuthControllerInterface {

    @Operation(summary = "Autenticar os dados informados.", description = "Verifica se seus dados consta no banco e cria um token de acesso.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Token criado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException;

    @Operation(summary = "Registrar um novo usuário", description = "Registre um novo usuário no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Verificar sessão", description = "Verifica se a sessão do usuário ainda está ativa na API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sessão verificada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )

    ResponseEntity<LoginWithIdDTO> loggedVerify() throws RegraDeNegocioException;

    @Operation(summary = "Esqueci minha senha!", description = "Insira seu email e caso esteja cadastrado no banco de dados, você irá receber um email com um token de acesso!")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Email verificado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<String> findEmail(@RequestBody @Valid String email);
}
