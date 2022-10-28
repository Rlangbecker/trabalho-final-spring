package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.UsuarioCreateDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.Usuario;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ObjectMapper objectMapper){
        this.usuarioRepository = usuarioRepository;
        this.objectMapper = objectMapper;
    }

    public UsuarioDTO create (UsuarioCreateDTO usuarioDto) throws RegraDeNegocioException, BancoDeDadosException {
        Usuario usuarioEntity = objectMapper.convertValue(usuarioDto, Usuario.class);

        Usuario usuario = usuarioRepository.adicionar(usuarioEntity);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuario, UsuarioDTO.class);

        return usuarioDTO;
    }
    // ListAll - ListbyName - ListById -
    public List<UsuarioDTO> list() throws BancoDeDadosException, RegraDeNegocioException {
        return usuarioRepository.listar()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();
    }

    public UsuarioDTO editarUsuario(Integer id, UsuarioDTO usuarioAtualizar) throws BancoDeDadosException, RegraDeNegocioException {
        Usuario usuarioRecuperado = findById(id);
        Usuario usuario = objectMapper.convertValue(usuarioAtualizar,Usuario.class);
        usuarioRecuperado.setNome(usuarioAtualizar.getNome());
        usuarioRecuperado.setSenha(usuarioAtualizar.getSenha());
        usuarioRecuperado.setEmail(usuarioAtualizar.getEmail());
        usuarioRecuperado.setTelefone(usuarioAtualizar.getTelefone());
        usuarioRecuperado.setDataNascimento(usuarioAtualizar.getDataNascimento());
        usuarioRecuperado.setSexo(usuarioAtualizar.getSexo());

        boolean conseguiuEditar = usuarioRepository.editar(id, usuarioRecuperado);

        return usuarioAtualizar;
    }

    public void removerUsuario(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        usuarioRepository.remover(id);
    }

    public UsuarioDTO listarUsuarioPorId(Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        try {
            Usuario usuarioRecuperado = usuarioRepository.listarUsuarioPorID(idUsuario);
            UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioRecuperado, UsuarioDTO.class);
            return usuarioDTO;
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException("Usuário não foi encontrado pelo ID.");
        }

    }

    public UsuarioDTO listarUsuarioPorNome(String nomeUsuario) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Usuario usuarioRecuperado = usuarioRepository.listarUsuarioPorNome(nomeUsuario);
            return objectMapper.convertValue(usuarioRecuperado, UsuarioDTO.class);
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException("Usuário não foi encontrado pelo nome.");
        }

    }



    public Usuario findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Usuario usuarioRecuperado = usuarioRepository.listarUsuarioPorID(id);
            return usuarioRecuperado;
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException("Usuário não cadastrado.");
        }

    }
}
