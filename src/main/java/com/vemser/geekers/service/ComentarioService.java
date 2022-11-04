package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.ComentarioCreateDTO;
import com.vemser.geekers.dto.ComentarioDTO;
import com.vemser.geekers.entity.Comentario;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public ComentarioDTO create(ComentarioCreateDTO comentarioDto) throws RegraDeNegocioException {
        try {
            usuarioService.findById(comentarioDto.getUsuarioEntity().getIdUsuario());
            Comentario comentarioEntity = objectMapper.convertValue(comentarioDto, Comentario.class);
            Comentario comentario = comentarioRepository.adicionar(comentarioEntity);
            ComentarioDTO comentarioDTO = objectMapper.convertValue(comentario, ComentarioDTO.class);
            return comentarioDTO;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro ao criar comentário no perfil Geeker, tente novamente mais tarde");
        }
    }

    public ComentarioDTO list(Integer idComentario) throws BancoDeDadosException, RegraDeNegocioException {
        Comentario comentario = findById(idComentario);
        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentario, ComentarioDTO.class);
        return comentarioDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        try {
            Comentario comentario = findById(id);
            comentarioRepository.remover(comentario.getIdComentario());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Falha ao tentar remover comentário Geeker");
        }
    }

    public ComentarioDTO editarComentario(Integer idComentario, ComentarioDTO atualizarComentario) throws RegraDeNegocioException {
        try {
            usuarioService.findById(atualizarComentario.getUsuarioEntity().getIdUsuario());
            Comentario comentarioRecuperado = findById(idComentario);
            comentarioRecuperado.setComentario(atualizarComentario.getComentario());
            comentarioRepository.editar(idComentario, comentarioRecuperado);
            return objectMapper.convertValue(comentarioRecuperado, ComentarioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Falha na tentativa de editar perfil Geeker");
        }
    }

    public List<ComentarioDTO> listarComentarioPorUsuario(Integer idUsuario) throws RegraDeNegocioException {
        try {
            usuarioService.findById(idUsuario);
            List<Comentario> comentarios = comentarioRepository.listarPorUsuario(idUsuario).stream().toList();
            return comentarios.stream()
                    .toList()
                    .stream()
                    .map(comentario -> objectMapper.convertValue(comentario, ComentarioDTO.class))
                    .toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Falha ao listar comentários do perfil Geeker");
        }
    }

    public Comentario findById(Integer idComentario) throws RegraDeNegocioException {
        try {
            Comentario comentario = comentarioRepository.listarComentarioPorId(idComentario);
            return comentario;
        } catch (BancoDeDadosException e) {
            throw  new RegraDeNegocioException("Comentário Geeker não foi encontrado pelo id ^_^");
        }
    }

}
