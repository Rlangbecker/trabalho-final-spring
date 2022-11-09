package com.vemser.geekers.controller;

import br.com.dbc.vemser.pessoaapi.dto.LoginDTO;
import com.vemser.geekers.entity.UsuarioLoginEntity;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.security.TokenService;
import com.vemser.geekers.service.UsuarioLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioLoginService usuarioLoginService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Operation(summary = "Autenticar dados", description = "Verifica se seus dados consta no banco e cria um token de acesso.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Token criado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getSenha()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // UsuarioLoginEntity
        Object principal = authenticate.getPrincipal();
        UsuarioLoginEntity usuarioLoginEntity = (UsuarioLoginEntity) principal;
        String token = tokenService.getToken(usuarioLoginEntity);
        return token;
    }
}
