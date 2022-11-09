package com.vemser.geekers.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String headerAuth = request.getHeader("Authorization");

        UsernamePasswordAuthenticationToken dtoSecurity = tokenService.isValid(headerAuth);


        SecurityContextHolder.getContext().setAuthentication(dtoSecurity); // se for null, ele vai definir como null,
        // e se for diferente de null, ele define como ele mesmo.

        filterChain.doFilter(request, response);
    }
}
