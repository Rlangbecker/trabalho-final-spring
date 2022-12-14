package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.*;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.enums.TipoEmail;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;


    public List<UsuarioDTO> list() throws RegraDeNegocioException {

        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();
    }


    public List<UsuarioDTO> listByAtivo(){
        return usuarioRepository.findByAtivo(TipoAtivo.ATIVO)
                .stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();
    }

    public List<UsuarioDTO> listByInativo() {
        return usuarioRepository.findByAtivo(TipoAtivo.INATIVO)
                .stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();
    }

    public void removerUsuario(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(id);
        usuarioEntity.setAtivo(TipoAtivo.INATIVO);
        usuarioRepository.save(usuarioEntity);
    }


    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario n??o encontrado"));
    }

    public UsuarioEntity findUserByEmail(String email) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email);

        return usuarioEntity;
    }

    public List<UsuarioDTO> findByName(String nome) {
        return usuarioRepository.findUsuarioEntityByNome(nome)
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();

    }

    public List<UsuarioMatchDTO> listarUsuarioEMatchs(Integer idUsuario) {
        return usuarioRepository.listarUsuarioEMatch(idUsuario);
    }

    public List<UsuarioDesafioDTO> listarUsuarioEDesafio(Integer idUsuario) {
        return usuarioRepository.listarUsuarioEDesafio(idUsuario);
    }

    public PageDTO<UsuarioDTO> listUsuarioPaginado(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<UsuarioEntity> paginaDoRepositorio = usuarioRepository.findAll(pageRequest);
        List<UsuarioDTO> usuarios = paginaDoRepositorio.getContent().stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();
        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                pagina,
                tamanho,
                usuarios
        );

    }

//    public UsuarioDTO atualizarCargo(Integer idUsuario, Integer idCargo) throws RegraDeNegocioException{
//        UsuarioEntity usuario = usuarioService.findById(idUsuario);
//        CargoEntity cargoEntity = cargoService.findById(idCargo);
//        cargoEntity.setUsuarios(Set.of(usuario));
//        usuario.setCargos(Set.of(cargoEntity));
//        usuarioRepository.save(usuario);
//        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuario, UsuarioDTO.class);
//        return usuarioDTO;
//    }

}
