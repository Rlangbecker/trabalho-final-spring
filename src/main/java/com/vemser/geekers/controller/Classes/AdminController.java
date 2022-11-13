package com.vemser.geekers.controller.Classes;

import com.vemser.geekers.controller.Interfaces.AdminControllerInterface;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.dto.UsuarioMatchDTO;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.UsuarioLoginService;
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
@RequestMapping("/admin")
@Validated
@Slf4j
@RequiredArgsConstructor
public class AdminController implements AdminControllerInterface {

    private final UsuarioService usuarioService;
    private final UsuarioLoginService usuarioLoginService;

    public ResponseEntity<List<UsuarioDTO>> listUsuarios() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.list(), HttpStatus.OK);
    }

    @GetMapping("/listar-usuario-matchs")
    public ResponseEntity<List<UsuarioMatchDTO>> listarUsuariosEMatchs(@RequestParam(required = false) Integer idPessoa) {
        return new ResponseEntity<>(usuarioService.listarUsuarioEMatchs(idPessoa), HttpStatus.OK);
    }


    @GetMapping("/inativos")
    public ResponseEntity<List<UsuarioDTO>> listUsuariosInativos() {
        return new ResponseEntity<>(usuarioService.listByInativo(), HttpStatus.OK);
    }

    @PutMapping("/alterar-cargo/{id}")
    public ResponseEntity<String> alterarCargo(@Valid @PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioLoginService.atualizarCargo(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id-usuario}")
    public ResponseEntity<Void> delete(@PathVariable("id-usuario") Integer idUsuario) throws RegraDeNegocioException {//
        log.info("Aguarde, removendo usuário . . .");
        usuarioService.removerUsuario(idUsuario);
        log.info("Perfil de usuário foi apagado.");
        return ResponseEntity.noContent().build();
    }
}
