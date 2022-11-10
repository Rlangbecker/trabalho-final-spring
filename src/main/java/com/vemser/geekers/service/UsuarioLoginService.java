package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.LoginDTO;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.entity.UsuarioLoginEntity;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.UsuarioLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioLoginService {
    private final UsuarioLoginRepository usuarioLoginRepository;
    private final ObjectMapper objectMapper;

    public LoginDTO create(LoginDTO loginDTO){
        UsuarioLoginEntity usuarioLoginEntity = objectMapper.convertValue(loginDTO, UsuarioLoginEntity.class);
        usuarioLoginEntity.setSenha(new BCryptPasswordEncoder().encode(loginDTO.getSenha()));
        usuarioLoginRepository.save(usuarioLoginEntity);
        return loginDTO;
    }

    public Optional<UsuarioLoginEntity> findByLoginAndSenha(String login, String senha) {
        return usuarioLoginRepository.findByLoginAndSenha(login, senha);
    }

    public Integer getIdLoggedUser() {
        Integer findUserId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findUserId;
    }

    public LoginWithIdDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioLoginEntity userLogged = findById(getIdLoggedUser());
        return objectMapper.convertValue(userLogged, LoginWithIdDTO.class);
    }

    public UsuarioLoginEntity findById(Integer idLoginUsuario) throws RegraDeNegocioException {
         UsuarioLoginEntity usuarioLoginEntity = usuarioLoginRepository.findById(idLoginUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));
         return usuarioLoginEntity;
    }



    public Optional<UsuarioLoginEntity> findByLogin(String login) {
        return usuarioLoginRepository.findByLogin(login);
    }
}
