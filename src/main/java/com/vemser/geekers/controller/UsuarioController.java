package com.vemser.geekers.controller;

import com.vemser.geekers.dto.*;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.UsuarioService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController implements UsuarioControllerInterface{

    private final UsuarioService usuarioService;

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

    @GetMapping("/listar-usuario-matchs")
    public List<UsuarioMatchDTO> listarUsuariosEMatchs(@RequestParam(required = false) Integer idPessoa) {
        return usuarioService.listarUsuarioEMatchs(idPessoa);
    }

    @GetMapping("/listar-usuario-desafio")
    public List<UsuarioDesafioDTO> listarUsuarioEDesafios(@RequestParam(required = false) Integer idPessoa) {
        return usuarioService.listarUsuarioEDesafio(idPessoa);
    }

    @GetMapping("/usuario-paginado")
    public PageDTO<UsuarioDTO> listarUsuarioPaginado(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina){
        return usuarioService.listUsuarioPaginado(paginaQueEuQuero, tamanhoDeRegistrosPorPagina);
    }

    @PutMapping("/change-password")
    public String trocarSenha(@Valid @RequestParam("Email") String email,
                              @Valid @RequestParam("Nova Senha") String senha) throws RegraDeNegocioException {
        return usuarioService.trocarSenha(email, senha);
    }

}
