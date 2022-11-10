package com.vemser.geekers.security;

import com.vemser.geekers.entity.UsuarioLoginEntity;
import com.vemser.geekers.service.UsuarioLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public class AuthenticationService implements UserDetailsService {

    private final UsuarioLoginService usuarioLoginService;

    @Override
    public UserDetails loadUserByUsername(String loginUsername) throws UsernameNotFoundException {
        Optional<UsuarioLoginEntity> usuarioOptional = usuarioLoginService.findByLogin(loginUsername);

        return usuarioOptional
                .orElseThrow(() -> new UsernameNotFoundException("Usuário inválido"));
    }
}
