package com.vemser.geekers.controller.Classes;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.vemser.geekers.dto.CupomDTO;
import com.vemser.geekers.dto.TopicoCupomDTO;
import com.vemser.geekers.service.ProdutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cupom")
@RestController
public class CupomController {
    private final ProdutorService produtorService;

    @PostMapping("/send-to")
    public ResponseEntity<Void> sendTo(@RequestBody TopicoCupomDTO topicoCupomDTO) throws JsonProcessingException {
        produtorService.enviarMensagem(topicoCupomDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar-por-valor-cupom")
    public ResponseEntity<List<CupomDTO>> filtrarPorValor(@RequestParam(required = false) Double valorCupom) {
        log.info("Filtrando busca, aguarde . . .");
        return ResponseEntity.ok(produtorService.filtrarPorValor(valorCupom));
    }
}
