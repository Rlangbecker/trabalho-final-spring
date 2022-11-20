package com.vemser.geekers.controller.Classes;

import com.vemser.geekers.controller.Interfaces.ComentarioControllerInterface;
import com.vemser.geekers.dto.ComentarioCreateDTO;
import com.vemser.geekers.dto.ComentarioDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.ComentarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/comentario")
public class ComentarioController implements ComentarioControllerInterface {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping("/{idUsuario}")
    public ResponseEntity<ComentarioDTO> create(@Valid @PathVariable("idUsuario") Integer idUsuario, @RequestBody ComentarioCreateDTO comentario) throws RegraDeNegocioException {
        log.info("Adicionando comentário.");
        ComentarioDTO criandoComentarioDto = comentarioService.create(idUsuario,comentario);
        log.info("Comentário adicionado.");
        return new ResponseEntity<>(criandoComentarioDto, HttpStatus.OK);
    }

    @GetMapping("/{id-usuario}/usuario")
    public ResponseEntity<List<ComentarioDTO>> listaComentarioPorIdUsuario(@PathVariable("id-usuario") Integer idComentario) throws RegraDeNegocioException  {
        return new ResponseEntity<>(comentarioService.listarComentarioPorUsuario(idComentario), HttpStatus.OK);
    }

    @GetMapping("/{id-comentario}/comentario")
    public ResponseEntity<ComentarioDTO> findByIdComentario(@PathVariable(name = "id-comentario") Integer idComentario) throws RegraDeNegocioException {
        return new ResponseEntity<>(comentarioService.findById(idComentario), HttpStatus.OK);
    }

    @PutMapping("/{id-comentario}")
    public ResponseEntity<ComentarioDTO> update(@PathVariable("id-comentario") Integer idComentario,
                                                @Valid @RequestBody ComentarioDTO atualizarComantario) throws RegraDeNegocioException{
        log.info("Atualizando comentário, aguarde . . .");
        ComentarioDTO comentarioAtualizado = comentarioService.editarComentario(idComentario, atualizarComantario);
        log.info("Comentário adicionado com sucesso.");
        return new ResponseEntity<>(comentarioAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id-comentario}/remover-comentario")
    public ResponseEntity<Void> delete(@PathVariable("id-comentario") Integer idUsuario) throws RegraDeNegocioException {
        comentarioService.delete(idUsuario);
        return ResponseEntity.noContent().build();
    }

}
