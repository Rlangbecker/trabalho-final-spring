package com.vemser.geekers.controller.Interfaces;

import com.vemser.geekers.dto.EventoCreateDTO;
import com.vemser.geekers.dto.EventoDTO;
import com.vemser.geekers.dto.EventoDTOContador;
import com.vemser.geekers.enums.TipoEvento;
import com.vemser.geekers.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;

public interface EventoControllerInterface {

    @Operation(summary = "Criar evento", description = "Cria um evento no aplicativo.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Criação de evento realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Cadastro gerou uma exceção")
            }
    )
    ResponseEntity<EventoDTO> create(@Valid @RequestParam("Data inicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
                                            @Valid @RequestParam("Data final") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
                                            @Valid @RequestParam("TipoEvento") TipoEvento tipoEvento,
                                            @Valid @RequestBody EventoCreateDTO evento) throws RegraDeNegocioException;

    @Operation(summary = "Contar eventos", description = "Conta quantos eventos tem presentes no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Busca gerou uma exceção")
            }
    )
    ResponseEntity<EventoDTOContador> countByEventos(@Valid @RequestParam("TipoEvento") TipoEvento tipoEvento);

    @Operation(summary = "Encontrar Evento", description = "Procura por um evento existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Busca gerou uma exceção")
            }
    )
    ResponseEntity<EventoDTO> findByEventos(@Valid @RequestParam("TipoEvento") TipoEvento tipoEvento);

    @Operation(summary = "Deletar um evento", description = "Deleta um evento do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleção realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Deleção gerou uma exceção")
            }
    )
    ResponseEntity<Void> delete(@PathVariable("id") String id) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar informações", description = "Atualiza as informações de um log de Matches no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Atualização gerou uma exceção")
            }
    )
    ResponseEntity<EventoDTO> updateStatus(@PathVariable("id") String id) throws  RegraDeNegocioException;
}
