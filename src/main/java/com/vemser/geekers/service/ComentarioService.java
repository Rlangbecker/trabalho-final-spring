package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.ComentarioDTO;
import com.vemser.geekers.entity.Comentario;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.ComentarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ObjectMapper objectMapper;

    public ComentarioService(ComentarioRepository comentarioRepository, ObjectMapper objectMapper) {
        this.comentarioRepository = comentarioRepository;
        this.objectMapper = objectMapper;
    }

    public ComentarioDTO create(@Valid ComentarioDTO comentarioDto) throws RegraDeNegocioException {
        Comentario comentarioEntity = objectMapper.convertValue(comentarioDto, Comentario.class);

        Comentario comentario = comentarioRepository.adicionar(comentarioEntity);
        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentario, ComentarioDTO.class);
        return comentarioDTO;
    }

    public ResponseEntity<List<ComentarioDTO>> findByUsuario(@PathVariable){
        return comentarioRepository.listar()
                .stream()
                .map(comentario -> objectMapper.convertValue(comentario, ComentarioDTO.class))
                .toList();

//        try {
//            List<Comentario> listar = comentarioRepository.listar();
//            listar.forEach(System.out::println);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    public void delete(Integer id) throws RegraDeNegocioException{
        Comentario comentarioDeletado = listarComentarioPorUsuario(id);

//        try {
//            boolean conseguiuRemover = comentarioRepository.remover(id);
//            System.out.println("Comentario removido?" + conseguiuRemover + " | com id=" + id);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    public ComentarioDTO editarComentario(Integer idComentario, ComentarioDTO atualizarComentario) throws RegraDeNegocioException {
        Comentario comentarioRecuperado = listarComentarioPorUsuario(idComentario);
        Comentario comentarioEntity = objectMapper.convertValue(atualizarComentario, Comentario.class);
        comentarioRecuperado.setComentario(atualizarComentario.getComentario());

        return objectMapper.convertValue(comentarioRecuperado, ComentarioDTO.class);
//        try {
//            boolean conseguiuEditar = comentarioRepository.editar(id, comentario);
//            System.out.println("Comentario editado? " + conseguiuEditar + " | com id=" + id);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    public ComentarioDTO listarComentarioPorUsuario(Integer idUsuario) throws RegraDeNegocioException {
        Comentario comentarioRecuperado = comentarioRepository.listar()
                .stream()
                .filter(comentario -> comentario.getIdComentario().equals(idUsuario))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Comentário não encontrado."));
        return comentarioRecuperado;
//        try {
//            List<Comentario> listar = comentarioRepository.listarPorUsuario(idUsuario);
//            for(Comentario comentario : listar){
//                System.out.println(comentario);
//            };
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

}
