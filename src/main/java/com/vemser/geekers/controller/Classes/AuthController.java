package com.vemser.geekers.controller.Classes;

import com.vemser.geekers.controller.Interfaces.AuthControllerInterface;
import com.vemser.geekers.dto.*;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.security.TokenService;
import com.vemser.geekers.service.UsuarioLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
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
public class AuthController implements AuthControllerInterface {

    private final UsuarioLoginService usuarioLoginService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getSenha()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // UsuarioEntity
        Object principal = authenticate.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;
        if (usuarioEntity.getAtivo() != TipoAtivo.INATIVO) {
            String token = tokenService.getToken(usuarioEntity);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("Conta desativada!", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioLoginService.create(usuarioCreateDTO));
    }
    @GetMapping("/logged")
    public ResponseEntity<LoginWithIdDTO> loggedVerify() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioLoginService.getLoggedUser(), HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> findEmail(@RequestBody @Valid String email){
        return new ResponseEntity<>(usuarioLoginService.findUserByEmail(email),HttpStatus.OK);
    }
}
