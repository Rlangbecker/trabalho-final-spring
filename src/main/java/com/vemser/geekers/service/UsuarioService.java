package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.*;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public UsuarioDTO create(UsuarioCreateDTO usuarioDto) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioDto, UsuarioEntity.class);
        UsuarioEntity usuario = usuarioRepository.save(usuarioEntity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuario, UsuarioDTO.class);
//        emailService.sendEmail(usuarioDTO,null, TipoEmail.CADASTRO);
        return usuarioDTO;
    }

    public List<UsuarioDTO> list() throws RegraDeNegocioException {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();
    }

    public UsuarioDTO editarUsuario(Integer id, UsuarioDTO usuarioAtualizar) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntityRecuperado = findById(id);
        usuarioEntityRecuperado.setNome(usuarioAtualizar.getNome());
        usuarioEntityRecuperado.setSenha(usuarioAtualizar.getSenha());
        usuarioEntityRecuperado.setEmail(usuarioAtualizar.getEmail());
        usuarioEntityRecuperado.setTelefone(usuarioAtualizar.getTelefone());
        usuarioEntityRecuperado.setDataNascimento(usuarioAtualizar.getDataNascimento());
        usuarioEntityRecuperado.setSexo(usuarioAtualizar.getSexo());

        usuarioRepository.save(usuarioEntityRecuperado);

        return objectMapper.convertValue(usuarioEntityRecuperado, UsuarioDTO.class);
    }

    public void removerUsuario(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(id);
        usuarioRepository.delete(usuarioEntity);
    }


    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
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
}
