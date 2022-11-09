package com.vemser.geekers.security;

import com.vemser.geekers.entity.UsuarioLoginEntity;
import com.vemser.geekers.service.UsuarioLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TokenService {

private final UsuarioLoginService usuarioLoginService;

    public String getToken(UsuarioLoginEntity usuarioEntity) {
        // FIXME por meio do usuário, gerar um token
        String tokenTexto = usuarioEntity.getLogin() + ";" + usuarioEntity.getSenha();
        String token = Base64.getEncoder().encodeToString(tokenTexto.getBytes());
        return token;
    }

    public Optional<UsuarioLoginEntity> isValid(String token) {
        // FIXME validar se o token é válido e retornar o usuário se for válido
        if(token == null) {
            return Optional.empty();
        }
        token = token.replace("Bearer ", ""); // token, example: dXZfbFgEH=

        byte[] decodedBytes = Base64.getUrlDecoder().decode(token);
        String decoded = new String(decodedBytes);
        String[] split = decoded.split(";");
        return usuarioLoginService.findByLoginAndSenha(split[0], split[1]);
    }
}
