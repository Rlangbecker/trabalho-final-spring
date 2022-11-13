package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.*;
import com.vemser.geekers.entity.CargoEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.enums.TipoEmail;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.CargoRepository;
import com.vemser.geekers.repository.UsuarioRepository;
import com.vemser.geekers.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioLoginService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final CargoRepository cargoRepository;

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {

        UsuarioEntity usuarioLoginEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);

        usuarioLoginEntity.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        usuarioLoginEntity.setAtivo(TipoAtivo.ATIVO);

        Optional<CargoEntity> cargo = cargoRepository.findById(2);

        usuarioLoginEntity.setCargos(Set.of(cargo.get()));

        usuarioRepository.save(usuarioLoginEntity);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioLoginEntity, UsuarioDTO.class);

        return usuarioDTO;
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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
        String token = tokenService.getTokenTemporary(usuarioEntity);
        usuarioSeguroDTO.setToken(token);
        emailService.sendEmailSeguro(usuarioSeguroDTO, TipoEmail.TROCA_SENHA);
        usuarioRepository.save(usuarioEntity);
        return "Email enviado com o token!";
    }


    public String trocarSenha(String senha) throws RegraDeNegocioException {
        LoginWithIdDTO login = getLoggedUser();
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(login.getEmail());
        if (usuarioEntity == null) {
            throw new RegraDeNegocioException("Usuário não encontrado!");
        }
        usuarioEntity.setSenha(passwordEncoder.encode(senha));
        usuarioRepository.save(usuarioEntity);
        return "Senha atualizada com sucesso!";
    }

    public UsuarioDTO editarUsuario(UsuarioDTO usuarioAtualizar) throws RegraDeNegocioException {
        LoginWithIdDTO login = getLoggedUser();
        UsuarioEntity usuarioEntityRecuperado = findById(login.getIdUsuario());
        usuarioEntityRecuperado.setNome(usuarioAtualizar.getNome());
        usuarioEntityRecuperado.setSenha(passwordEncoder.encode(usuarioAtualizar.getSenha()));

        usuarioEntityRecuperado.setEmail(usuarioAtualizar.getEmail());
        usuarioEntityRecuperado.setTelefone(usuarioAtualizar.getTelefone());
        usuarioEntityRecuperado.setDataNascimento(usuarioAtualizar.getDataNascimento());
        usuarioEntityRecuperado.setSexo(usuarioAtualizar.getSexo());

        usuarioRepository.save(usuarioEntityRecuperado);

        return objectMapper.convertValue(usuarioEntityRecuperado, UsuarioDTO.class);
    }

    public String atualizarCargo(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = findById(idUsuario);
        Optional<CargoEntity> cargo = cargoRepository.findById(3);
        Set<CargoEntity> cargoSet = new HashSet<>();
        cargoSet.add(cargo.get());
        usuarioEncontrado.setCargos(cargoSet);
        usuarioRepository.save(usuarioEncontrado);
        return "Usuario atualizado para Usuario Gold";
    }

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }


}
