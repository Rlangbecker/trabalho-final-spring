package com.vemser.geekers.controller;

import com.vemser.geekers.dto.LoginDTO;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.dto.UsuarioCreateDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.security.TokenService;
import com.vemser.geekers.service.UsuarioLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        // UsuarioEntity
        Object principal = authenticate.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;
        String token = tokenService.getToken(usuarioEntity);
        return token;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioLoginService.create(usuarioCreateDTO));
    }
    @GetMapping("/logged")
    public ResponseEntity<LoginWithIdDTO> findByLogin() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioLoginService.getLoggedUser(), HttpStatus.OK);
    }
}