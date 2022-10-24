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

        return objectMapper.convertValue(usuarioRecuperado, UsuarioDTO.class);
    }

    public UsuarioDTO listarUsuarioPorId(Integer idUsuario) throws BancoDeDadosException, RegraDeNegocioException {
        Usuario usuarioRecuperado = (Usuario) usuarioRepository.listar()
                .stream()
                .map(usuario -> usuario.getIdUsuario().equals(idUsuario))
                .toList();
        Usuario usuarioEntity = objectMapper.convertValue(usuarioRecuperado, Usuario.class);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);

        return usuarioDTO;
    }

    public void listarPorUsuarios(Integer id_usuario) {
        try {
            List<Usuario> listar = usuarioRepository.listarPorUsuario(id_usuario);
            listar.forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }


    public void listarUsuariosPorUsuario(Integer quantidadeUsuarios) {
        try {
            List<Usuario> listar = usuarioRepository.listarPorUsuario(quantidadeUsuarios);
            listar.forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
    }

    public void removerUsuario(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        usuarioRepository.remover(id);
    }

    public Usuario verificarUsuario (Usuario usuario) {
        try {
            return usuarioRepository.receberUsuario(usuario);
        } catch (BancoDeDadosException e) {
            System.err.println("ERRO: " + e.getMessage());
            e.printStackTrace();
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Usuario findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Usuario usuarioRecuperado = usuarioRepository.listar()
                .stream()
                .filter(pessoa -> pessoa.getIdUsuario().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não cadastrado."));

        return usuarioRecuperado;
    }
}