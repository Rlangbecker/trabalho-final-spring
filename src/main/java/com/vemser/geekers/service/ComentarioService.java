package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.ComentarioCreateDTO;
import com.vemser.geekers.dto.ComentarioDTO;
import com.vemser.geekers.entity.ComentarioEntity;
import com.vemser.geekers.entity.UsuarioEntity;
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

    public ComentarioDTO create(Integer idUsuario, ComentarioCreateDTO comentarioCreateDTO) throws RegraDeNegocioException {

        ComentarioEntity comentarioEntity = objectMapper.convertValue(comentarioCreateDTO, ComentarioEntity.class);

        comentarioEntity.setUsuario(usuarioService.findById(idUsuario));
        comentarioEntity.getUsuario().setIdUsuario(idUsuario);

        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentarioRepository.save(comentarioEntity), ComentarioDTO.class);
        comentarioDTO.setIdUsuario(idUsuario);
        return comentarioDTO;
    }

    public ComentarioDTO list(Integer idComentario) throws BancoDeDadosException, RegraDeNegocioException {

        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentarioRepository.findById(idComentario),ComentarioDTO.class);

        return comentarioDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        comentarioRepository.deleteById(id);
    }

    public ComentarioDTO editarComentario(Integer idComentario, ComentarioDTO atualizarComentario) throws RegraDeNegocioException {

        UsuarioEntity usuario = usuarioService.findById(atualizarComentario.getIdUsuario());

        ComentarioEntity comentarioEntityRecuperado = objectMapper.convertValue(findById(idComentario), ComentarioEntity.class);

        comentarioEntityRecuperado.setUsuario(usuario);
        comentarioEntityRecuperado.setComentario(atualizarComentario.getComentario());
        comentarioRepository.save(comentarioEntityRecuperado);

        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentarioEntityRecuperado, ComentarioDTO.class);

        comentarioDTO.setIdUsuario(atualizarComentario.getIdUsuario());

        return comentarioDTO;
    }

    public List<ComentarioDTO> listarComentarioPorUsuario(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuario = usuarioService.findById(idUsuario);


        List<ComentarioDTO> comentarios = comentarioRepository.findComentarioEntityByUsuario(usuario).stream()
                .map(comentarioEntity -> objectMapper.convertValue(comentarioEntity, ComentarioDTO.class))
                .toList();



        return comentarios;
    }



    public ComentarioDTO findById(Integer idComentario) {

       return objectMapper.convertValue(comentarioRepository.findById(idComentario), ComentarioDTO.class);

    }

}
