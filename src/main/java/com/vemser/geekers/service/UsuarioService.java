package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.UsuarioCreateDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.Usuario;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.support.NullValue;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final Integer QUANTIDADE_USUARIOS = 3;

    public UsuarioDTO create(UsuarioCreateDTO usuarioDto) throws RegraDeNegocioException, BancoDeDadosException {
        Usuario usuarioEntity = objectMapper.convertValue(usuarioDto, Usuario.class);

        Usuario usuario = usuarioRepository.adicionar(usuarioEntity);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuario, UsuarioDTO.class);

        return usuarioDTO;
    }

    // ListAll - ListbyName - ListById -
    // Busca no banco de No máximo QUANTIDADE_USUARIOS (3)
    public List<UsuarioDTO> listaQuantidadeUsuarios() throws BancoDeDadosException, RegraDeNegocioException {
        try {
            return usuarioRepository.listarUsuariosRandom(QUANTIDADE_USUARIOS)
                    .stream()
                    .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao buscar Geekers ^_^");
        }
    }

    public UsuarioDTO editarUsuario(Integer id, UsuarioDTO usuarioAtualizar) throws BancoDeDadosException, RegraDeNegocioException {
        Usuario usuarioRecuperado = findById(id);
//        Usuario usuario = objectMapper.convertValue(usuarioAtualizar,Usuario.class);
        usuarioRecuperado.setNome(usuarioAtualizar.getNome());
        usuarioRecuperado.setSenha(usuarioAtualizar.getSenha());
        usuarioRecuperado.setEmail(usuarioAtualizar.getEmail());
        usuarioRecuperado.setTelefone(usuarioAtualizar.getTelefone());
        usuarioRecuperado.setDataNascimento(usuarioAtualizar.getDataNascimento());
        usuarioRecuperado.setSexo(usuarioAtualizar.getSexo());

        usuarioRepository.editar(id, usuarioRecuperado);

        return usuarioAtualizar;
    }

    public void removerUsuario(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        usuarioRepository.remover(id);
    }

    //ARRUMAR RETORNO NULL
    public UsuarioDTO listarUsuarioPorId(Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException {
        Usuario usuario = findById(idUsuario);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuario, UsuarioDTO.class);
        return usuarioDTO;

//        try {
//            Usuario usuario = findById(idUsuario);
//            Usuario usuarioRecuperado = usuarioRepository.listarUsuarioPorID(idUsuario);
//            UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioRecuperado, UsuarioDTO.class);
//            return usuarioDTO;
//
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro ao buscar Geekers ^_^");
//        }
    }

    public UsuarioDTO listarUsuarioPorNome(String nomeUsuario) throws RegraDeNegocioException {
        try {
            Usuario usuarioRecuperado = usuarioRepository.listarUsuarioPorNome(nomeUsuario.toUpperCase());
            return objectMapper.convertValue(usuarioRecuperado, UsuarioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Usuário não foi encontrado pelo nome.");
        }

    }

    public Usuario findById(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        Usuario usuario = usuarioRepository.listarUsuarioPorID(id);
        if(usuario == null) {
            throw new RegraDeNegocioException("Usuário não encontrado");
        }
        return  usuario;

//        try {
//            Usuario usuarioRecuperado = usuarioRepository.listarUsuarioPorID(id);
//            return usuarioRecuperado;
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Usuário não cadastrado.");
//        }
    }
}
