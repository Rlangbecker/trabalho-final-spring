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
    private final ObjectMapper objectMapper;

    public ComentarioDTO create(ComentarioCreateDTO comentarioDto) throws RegraDeNegocioException, BancoDeDadosException {
        Comentario comentarioEntity = objectMapper.convertValue(comentarioDto, Comentario.class);
        Comentario comentario = comentarioRepository.adicionar(comentarioEntity);
        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentario, ComentarioDTO.class);
        return comentarioDTO;
    }

    public ComentarioDTO list(Integer idComentario) throws BancoDeDadosException, RegraDeNegocioException {
        Comentario comentario = findById(idComentario);
        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentario, ComentarioDTO.class);
        return comentarioDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        comentarioRepository.remover(id);
    }

    public ComentarioDTO editarComentario(Integer idComentario, ComentarioDTO atualizarComentario) throws RegraDeNegocioException, BancoDeDadosException {
        Comentario comentarioRecuperado = findById(idComentario);
        comentarioRecuperado.setComentario(atualizarComentario.getComentario());
        comentarioRepository.editar(idComentario, comentarioRecuperado);
        return objectMapper.convertValue(comentarioRecuperado, ComentarioDTO.class);
    }

    public List<ComentarioDTO> listarComentarioPorUsuario(Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException {
        List<Comentario> comentarios = comentarioRepository.listarPorUsuario(idUsuario).stream().toList();
        return comentarios.stream()
                .toList()
                .stream()
                .map(comentario -> objectMapper.convertValue(comentario, ComentarioDTO.class))
                .toList();
    }

    public Comentario findById(Integer idComentario) throws BancoDeDadosException {
        Comentario comentario = comentarioRepository.listarComentarioPorId(idComentario);
        return comentario;
    }

}
