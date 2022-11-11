package com.vemser.geekers.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth.antMatchers("/", "/auth/**").permitAll()

                        //USUARIO

                        // -> listas
                        .antMatchers("/usuario/listar-usuario-desafio").hasRole("USUARIO")
                        .antMatchers("/usuario/by-nome").hasRole("USUARIO")
                        .antMatchers(HttpMethod.PUT, "/usuario").hasRole("USUARIO")

                        // -> desafio
                        .antMatchers(HttpMethod.POST, "/desafio").hasRole("USUARIO")
                        .antMatchers(HttpMethod.DELETE, "/desafio").hasRole("USUARIO")
                        .antMatchers(HttpMethod.PUT, "/desafio").hasRole("USUARIO")
                        .antMatchers(HttpMethod.GET, "/desafio/usuario").hasRole("USUARIO")

                        // -> hobbie
                        .antMatchers("/hobbie/**").hasAnyRole("USUARIO", "ADMIN")

                        // -> comentários
                        .antMatchers(HttpMethod.PUT, "/comentario").hasRole("USUARIO")
                        .antMatchers(HttpMethod.POST, "/comentario").hasRole("USUARIO")
//                        .antMatchers(HttpMethod.GET, "/comentario/**/usuario").hasRole("USUARIO")

                        //ADMIN


                        .anyRequest().authenticated());
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
               registry.addMapping("/**")
                       .allowedMethods("*")
                       .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // fazer o Gerador para poder funcionar
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}