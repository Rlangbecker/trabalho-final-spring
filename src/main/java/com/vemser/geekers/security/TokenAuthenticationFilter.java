package com.vemser.geekers.security;

import com.vemser.geekers.entity.UsuarioLoginEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // FIXME recuperar token do header
        String headerAuth = request.getHeader("Authorization");
        Optional<UsuarioLoginEntity> isValid = tokenService.isValid(headerAuth);

        if(isValid.isPresent()) {
            UsuarioLoginEntity usuarioEntity = isValid.get();
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usuarioEntity.getLogin(),
                    usuarioEntity.getSenha(), Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(token);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        filterChain.doFilter(request, response);
    }
}
