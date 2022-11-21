package com.vemser.geekers.controller.Interfaces;

import com.vemser.geekers.dto.LogDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface LogMatchControllerInterface {
    @Operation(summary = "Procurar por data", description = "Faz uma busca pela data de um log presente no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Busca gerou uma exceção")
            }
    )
    ResponseEntity<List<LogDTO>> findByDate(@RequestParam("Data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                            LocalDate data);
    @Operation(summary = "Listar todos os logs", description = "Faz uma busca pelos logs presentes no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Busca de logs realizada com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para se cadastrar neste recurso"),
                    @ApiResponse(responseCode = "500", description = "Busca gerou uma exceção")
            }
    )
    ResponseEntity<List<LogDTO>> findAll();
}
