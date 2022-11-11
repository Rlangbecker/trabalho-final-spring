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
                .authorizeHttpRequests((auth) -> auth.antMatchers("/auth", "/auth/**").permitAll()

                        // LISTAS
                        // -> USUARIO
                        .antMatchers("/usuario/listar-usuario-desafio").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers("/usuario/by-nome").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.PUT, "/usuario").hasAnyRole("USUARIO", "ADMIN")
                        // -> ADMIN
                        .antMatchers(HttpMethod.DELETE, "/usuario/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/usuario/**").hasRole("ADMIN")
                        // DESAFIOS
                        .antMatchers("/desafio/**").hasAnyRole("USUARIO", "ADMIN")
                        // HOBBIES
                        .antMatchers("/hobbie/**").hasAnyRole("USUARIO", "ADMIN")
                        // COMENTÁRIOS
                        // -> USUARIO
                        .antMatchers(HttpMethod.PUT, "/comentario").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.POST, "/comentario").hasAnyRole("USUARIO", "ADMIN")
                        .antMatchers(HttpMethod.GET, "/comentario/**/usuario").hasAnyRole("USUARIO", "ADMIN")
                        // -> ADMIN
                        .antMatchers(HttpMethod.DELETE, "/comentario/**").hasRole("ADMIN")

                        // MATCH
//                        .antMatchers(HttpMethod.POST, "/mat")


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
