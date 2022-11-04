//package com.vemser.geekers.controller;
//
//import com.vemser.geekers.dto.ComentarioCreateDTO;
//import com.vemser.geekers.dto.ComentarioDTO;
//import com.vemser.geekers.exception.BancoDeDadosException;
//import com.vemser.geekers.exception.RegraDeNegocioException;
//import com.vemser.geekers.service.ComentarioService;
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@Validated
//@Slf4j
//@RequestMapping("/comentario")
//public class ComentarioController implements ComentarioControllerInterface {
//
//    private final ComentarioService comentarioService;
//
//    public ComentarioController(ComentarioService comentarioService) {
//        this.comentarioService = comentarioService;
//    }
//
//    @PostMapping
//    public ResponseEntity<ComentarioDTO> create(@Valid @RequestBody ComentarioCreateDTO comentario) throws RegraDeNegocioException, BancoDeDadosException {
//        log.info("Adicionando comentário.");
//        ComentarioDTO criandoComentarioDto = comentarioService.create(comentario);
//        log.info("Comentário adicionado.");
//        return new ResponseEntity<>(criandoComentarioDto, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id-usuario}/usuario")
//    public ResponseEntity<List<ComentarioDTO>> listaComentarioPorIdUsuario(@PathVariable("id-usuario") Integer idComentario) throws RegraDeNegocioException, BancoDeDadosException {
//        return new ResponseEntity<>(comentarioService.listarComentarioPorUsuario(idComentario), HttpStatus.OK);
//    }
//
//    @GetMapping("/{id-comentario}/comentario")
//    public ResponseEntity<ComentarioDTO> listarPorIdComentario(@PathVariable(name = "id-comentario") Integer idComentario) throws RegraDeNegocioException, BancoDeDadosException {
//        return new ResponseEntity<>(comentarioService.list(idComentario), HttpStatus.OK);
//    }
//
//    @Operation(hidden = true)
//    @GetMapping
//    public ResponseEntity<List<ComentarioDTO>> list() throws RegraDeNegocioException, BancoDeDadosException {
//        return null;
//    }
//
//    @PutMapping("/{id-comentario}")
//    public ResponseEntity<ComentarioDTO> update(@PathVariable("id-comentario") Integer idComentario,
//                                                @Valid @RequestBody ComentarioDTO atualizarComantario) throws RegraDeNegocioException, BancoDeDadosException {
//        log.info("Atualizando comentário, aguarde . . .");
//        ComentarioDTO comentarioAtualizado = comentarioService.editarComentario(idComentario, atualizarComantario);
//        log.info("Comentário adicionado com sucesso.");
//        return new ResponseEntity<>(comentarioAtualizado, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id-comentario}/remover-comentario")
//    public ResponseEntity<Void> delete(@PathVariable("id-comentario") Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException {
//        comentarioService.delete(idUsuario);
//        return ResponseEntity.noContent().build();
//    }
//
//}
