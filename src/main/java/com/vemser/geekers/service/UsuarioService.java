package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.UsuarioCreateDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoEmail;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
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

    // Busca no banco de No máximo QUANTIDADE_USUARIOS (3)
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
        try {
            UsuarioEntity usuarioEntity = findById(id);
            usuarioRepository.delete(usuarioEntity);
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException("Falha na tentativa de remover seu perfil do Geekers");
        }
    }


    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        try {
            return usuarioRepository.findById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
        } catch ( RegraDeNegocioException e) {
            throw new RegraDeNegocioException("Geeker não foi encontrado pelo id ^_^");
        }
    }

    public List<UsuarioDTO> findByName(String nome) throws RegraDeNegocioException {
        return usuarioRepository.findUsuarioEntityByNome(nome)
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();

    }
}
