package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.UsuarioCreateDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.Usuario;
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
    private final Integer QUANTIDADE_USUARIOS = 3;

    public UsuarioDTO create(UsuarioCreateDTO usuarioDto) throws RegraDeNegocioException {
        try {
            Usuario usuarioEntity = objectMapper.convertValue(usuarioDto, Usuario.class);
            Usuario usuario = usuarioRepository.adicionar(usuarioEntity);
            UsuarioDTO usuarioDTO = objectMapper.convertValue(usuario, UsuarioDTO.class);

            return usuarioDTO;
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }
    }

    // ListAll - ListbyName - ListById -
    // Busca no banco de No máximo QUANTIDADE_USUARIOS (3)
    public List<UsuarioDTO> listaQuantidadeUsuarios() throws RegraDeNegocioException {
        try {
            return usuarioRepository.listarUsuariosRandom(QUANTIDADE_USUARIOS)
                    .stream()
                    .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao buscar Geekers ^_^");
        }
    }

    public UsuarioDTO editarUsuario(Integer id, UsuarioDTO usuarioAtualizar) throws RegraDeNegocioException {
        try {
            Usuario usuarioRecuperado = findById(id);
            usuarioRecuperado.setNome(usuarioAtualizar.getNome());
            usuarioRecuperado.setSenha(usuarioAtualizar.getSenha());
            usuarioRecuperado.setEmail(usuarioAtualizar.getEmail());
            usuarioRecuperado.setTelefone(usuarioAtualizar.getTelefone());
            usuarioRecuperado.setDataNascimento(usuarioAtualizar.getDataNascimento());
            usuarioRecuperado.setSexo(usuarioAtualizar.getSexo());

            usuarioRepository.editar(id, usuarioRecuperado);

            return objectMapper.convertValue(usuarioRecuperado, UsuarioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Falha ao editar Geeker, tente novamente mais tarde");
        }
    }

    public void removerUsuario(Integer id) throws RegraDeNegocioException {
        try {
            Usuario usuario = findById(id);
            usuarioRepository.remover(usuario.getIdUsuario());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Falha na tentativa de remover seu perfil do Geekers");
        }
    }

    //ARRUMAR RETORNO NULL
    public UsuarioDTO listarUsuarioPorId(Integer idUsuario) throws RegraDeNegocioException {
        Usuario usuario = findById(idUsuario);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuario, UsuarioDTO.class);
        return usuarioDTO;
    }

    public UsuarioDTO listarUsuarioPorNome(String nomeUsuario) throws RegraDeNegocioException {
        Usuario usuario = findByName(nomeUsuario);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuario, UsuarioDTO.class);
        return  usuarioDTO;

//        try {
//            Usuario usuarioRecuperado = usuarioRepository.listarUsuarioPorNome(nomeUsuario.toUpperCase());
//            if(usuarioRecuperado.getNome() == null){
//                throw new RegraDeNegocioException("Geeker nome");
//            }
//            return objectMapper.convertValue(usuarioRecuperado, UsuarioDTO.class);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Geeker não foi encontrado pelo nome ^_^");
//        }

    }

    public Usuario findById(Integer id) throws RegraDeNegocioException {
        try {
            Usuario usuario = usuarioRepository.listarUsuarioPorID(id);
            return usuario;
        } catch ( BancoDeDadosException e) {
            throw new RegraDeNegocioException("Geeker não foi encontrado pelo id ^_^");
        }
    }

    public Usuario findByName(String nome) throws RegraDeNegocioException {
        try {
            Usuario usuario = usuarioRepository.listarUsuarioPorNome(nome.toUpperCase());
            if(usuario.getNome() == null) {
                throw new RegraDeNegocioException("Geeker não foi encontrado pelo nome ^_^");
            }
            return usuario;
        } catch ( BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro de conexão ao banco Geeker, tente novamente mais tarde");
        }
    }
}
