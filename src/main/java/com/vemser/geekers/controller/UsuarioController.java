package com.vemser.geekers.controller;

import com.vemser.geekers.dto.UsuarioCreateDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.UsuarioService;
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
@RequestMapping("/usuario")
public class UsuarioController implements UsuarioControllerInterface{

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioCreateDTO usuarioDto) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Iniciando cadastro de usuário . . .");
        UsuarioDTO criandoUsuarioDto = usuarioService.create(usuarioDto);
        log.info("Usuário cadastrado.");

        return new ResponseEntity<>(criandoUsuarioDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listUsuarios() throws RegraDeNegocioException, BancoDeDadosException {
        return new ResponseEntity<>(usuarioService.list(), HttpStatus.OK);
    }

    @GetMapping("/{id-usuario}")
    public ResponseEntity<UsuarioDTO> listUsuarioPorId(@PathVariable(name = "id-usuario") Integer idUsuario) throws RegraDeNegocioException {
        return null;
    }

    @GetMapping("/by-nome")
    public ResponseEntity<List<UsuarioDTO>> ListarPorNome(@RequestParam("nome") String nome) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.findByName(nome), HttpStatus.OK);
    }

    @PutMapping("/{id-usuario}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable("id-usuario") Integer idUsuario,
                                             @Valid @RequestBody UsuarioDTO atualizarUsuario) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Atualizando perfil do usuário, aguarde . . .");
        UsuarioDTO usuarioAtualizado = usuarioService.editarUsuario(idUsuario, atualizarUsuario);
        log.info("Perfil de usuário foi atualizado.");

        return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id-usuario}")
    public ResponseEntity<Void> delete(@PathVariable("id-usuario") Integer idUsuario) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Aguarde, removendo usuário . . .");
        usuarioService.removerUsuario(idUsuario);
        log.info("Perfil de usuário foi apagado.");
        return ResponseEntity.noContent().build();
    }

}
