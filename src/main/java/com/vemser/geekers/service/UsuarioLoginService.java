package com.vemser.geekers.service;

import com.vemser.geekers.entity.UsuarioLoginEntity;
import com.vemser.geekers.repository.UsuarioLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioLoginService {
    private final UsuarioLoginRepository usuarioLoginRepository;

    // FIXME construir métodoss necessários para o usuário

    public Optional<UsuarioLoginEntity> findByLoginAndSenha(String login, String senha) {
        return usuarioLoginRepository.findByLoginAndSenha(login, senha);
    }

    public Optional<UsuarioLoginEntity> findById(Integer idLoginUsuario) {
        return usuarioLoginRepository.findById(idLoginUsuario);
    }

    public Optional<UsuarioLoginEntity> findByLogin(String login) {
        return usuarioLoginRepository.findByLogin(login);
    }
}
