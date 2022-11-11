package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.LoginDTO;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.dto.UsuarioCreateDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.CargoEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
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

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioLoginEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        usuarioLoginEntity.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        CargoEntity cargoEntity = cargoService.findById(2);
        cargoEntity.setUsuarios(Set.of(usuarioLoginEntity));
        usuarioLoginEntity.setCargos(Set.of(cargoEntity));
        usuarioRepository.save(usuarioLoginEntity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioLoginEntity, UsuarioDTO.class);
        return usuarioDTO;
    }

    public Integer getIdLoggedUser() {
        Integer findUserId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findUserId;
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



    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
