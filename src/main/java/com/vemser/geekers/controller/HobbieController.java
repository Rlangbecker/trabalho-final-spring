package com.vemser.geekers.controller;

import com.vemser.geekers.dto.HobbieCreateDTO;
import com.vemser.geekers.dto.HobbieDTO;
import com.vemser.geekers.entity.Hobbie;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.service.HobbieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/hobbie") // localhost:8080/hobbie
public class HobbieController {

    private final HobbieService hobbieService;

    @Operation(summary = "Criar hobbies", description = "Cria um hobbie com o ID do usuario passado no body")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria um hobbie para o Usuario informado no body"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idUsuario}") // localhost:8080/hobbie/idUsuario
    public ResponseEntity<HobbieDTO> create(@PathVariable("idUsuario") Integer idUsuario , @RequestBody HobbieDTO hobbieDTO) throws RegraDeNegocioException, BancoDeDadosException {
        HobbieDTO hDTO= hobbieService.create(idUsuario,hobbieDTO);
        return new ResponseEntity<>(hDTO, HttpStatus.OK) ;
    }


    @Operation(summary = "listar hobbies", description = "Lista todos os hobbie do usuario passado por ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de hobbies do Usuario"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("{idUsuario}") // localhost:8080/hobbie/idUsuario
    public List<Hobbie> listByIdUsuario(@PathVariable ("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        log.info("Listando Hobbies | idUsuario: " + idUsuario);
        return hobbieService.listByIdUsuario(idUsuario);
    }


    @Operation(summary = "Atualizar hobbie", description = "Atualiza o hobbie do usuario passado por ID(Hobbie)")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza o hobbie do Usuario por ID do Hobbie"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idHobbie}") // localhost:8080/hobbie/idHobbie
    public ResponseEntity<HobbieDTO> update(@PathVariable("idHobbie") Integer idHobbie,
                         @RequestBody HobbieCreateDTO hobbieAtualizar) throws RegraDeNegocioException {
        HobbieDTO hobbieDTO = hobbieService.editar(idHobbie,hobbieAtualizar);
        //VERIFICAR SE VAI FICAR POR IDHOBBIE OU IDUSUARIO
        return new ResponseEntity<>(hobbieDTO, HttpStatus.OK);
    }



    @Operation(summary = "Deletar hobbie", description = "Deleta o hobbie do usuario passado por ID(Hobbie)")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta o hobbie do Usuario por ID do Hobbie"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idHobbie}") // localhost:8080/hobbie/idHobbie
    public void delete(@PathVariable("idHobbie") Integer idHobbie) throws RegraDeNegocioException, BancoDeDadosException {
        hobbieService.remover(idHobbie);
    }


}
