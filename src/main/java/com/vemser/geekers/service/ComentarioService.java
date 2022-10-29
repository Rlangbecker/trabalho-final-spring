package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.ComentarioCreateDTO;
import com.vemser.geekers.dto.ComentarioDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.Comentario;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.ComentarioRepository;
import freemarker.core.ReturnInstruction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ObjectMapper objectMapper;

    public ComentarioDTO create(@Valid ComentarioCreateDTO comentarioDto) throws RegraDeNegocioException, BancoDeDadosException {
        Comentario comentarioEntity = objectMapper.convertValue(comentarioDto, Comentario.class);
        Comentario comentario = comentarioRepository.adicionar(comentarioEntity);
        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentario, ComentarioDTO.class);
        return comentarioDTO;
    }

    public ComentarioDTO list(Integer idComentario) throws BancoDeDadosException, RegraDeNegocioException {
        Comentario comentario = findById(idComentario);
        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentario, ComentarioDTO.class);
        return comentarioDTO;

//        return comentarioRepository.listarComentarioPorId(idComentario)
//                .stream()
//                .map(comentario -> objectMapper.convertValue(comentario, ComentarioDTO.class))
//                .toList();
    }

    public void delete(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
//        Comentario comentarioDeletado =
        comentarioRepository.remover(id);

//        try {
//            boolean conseguiuRemover = comentarioRepository.remover(id);
//            System.out.println("Comentario removido?" + conseguiuRemover + " | com id=" + id);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    public ComentarioDTO editarComentario(Integer idComentario, ComentarioCreateDTO atualizarComentario) throws RegraDeNegocioException, BancoDeDadosException {
//        Comentario comentarioRecuperado = listarComentarioPorUsuario(idComentario);
        Comentario comentarioEntity = objectMapper.convertValue(atualizarComentario, Comentario.class);
//        comentarioRecuperado.setComentario(atualizarComentario.getComentario());
        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentarioRepository.editar(idComentario, comentarioEntity), ComentarioDTO.class);
        return comentarioDTO;
    }

    public List<ComentarioDTO> listarComentarioPorUsuario(Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException {
        List<Comentario> comentarios = comentarioRepository.listarPorUsuario(idUsuario).stream().toList();
//        List<Comentario> comentarioList = objectMapper.convertValue(comentarios, ComentarioDTO.class);
//
//        return comentarios;
        return comentarios.stream()
                .toList()
                .stream()
                .map(comentario -> objectMapper.convertValue(comentario, ComentarioDTO.class))
                .toList();


//                .filter(comentario -> comentario.getIdComentario().equals(idUsuario))
//                .findFirst()
//                .map(comentario -> objectMapper.convertValue(comentario, ComentarioDTO.class))
//                .orElseThrow(() -> new RegraDeNegocioException("Comentário não encontrado."));
    }

    public Comentario findById(Integer idComentario) throws BancoDeDadosException {
        Comentario comentario = comentarioRepository.listarComentarioPorId(idComentario);
        return comentario;
    }

}
