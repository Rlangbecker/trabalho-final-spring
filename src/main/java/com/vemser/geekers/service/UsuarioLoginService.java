package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.*;
import com.vemser.geekers.entity.CargoEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.enums.TipoEmail;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.UsuarioRepository;
import com.vemser.geekers.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioLoginService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final CargoService cargoService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final TokenService tokenService;

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioLoginEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        usuarioLoginEntity.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        usuarioLoginEntity.setAtivo(TipoAtivo.ATIVO);
        CargoEntity cargoEntity = cargoService.findById(2);
        cargoEntity.setUsuarios(Set.of(usuarioLoginEntity));
        usuarioLoginEntity.setCargos(Set.of(cargoEntity));
        usuarioRepository.save(usuarioLoginEntity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioLoginEntity, UsuarioDTO.class);
        return usuarioDTO;
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt( (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public LoginWithIdDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioEntity userLogged = findById(getIdLoggedUser());
        return objectMapper.convertValue(userLogged, LoginWithIdDTO.class);
    }

    public UsuarioEntity findById(Integer idLoginUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(idLoginUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));
         return usuarioEntity;
    }

    public String findUserByEmail(String email) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email);
        UsuarioSeguroDTO usuarioSeguroDTO = objectMapper.convertValue(usuarioEntity, UsuarioSeguroDTO.class);
        // UsuarioEntityPrincipal
        String token = tokenService.getToken(usuarioEntity);
        usuarioSeguroDTO.setToken(token);
        emailService.sendEmailSeguro(usuarioSeguroDTO,TipoEmail.TROCA_SENHA);
        usuarioRepository.save(usuarioEntity);
        return "Email enviado com o token!";
    }

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
