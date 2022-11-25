package com.vemser.geekers.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.CupomDTO;
import com.vemser.geekers.dto.TopicoCupomDTO;
import com.vemser.geekers.entity.CupomEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.CupomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumidorService {
    private final ObjectMapper objectMapper;
    private final CupomRepository cupomRepository;
    private final UsuarioService usuarioService;
    private final UsuarioLoginService usuarioLoginService;

    @KafkaListener(
            clientIdPrefix = "${spring.kafka.consumer.client-id}",
            groupId = "${spring.kafka.consumer.group-id}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions = {"1"})}
    )
    public void consumirCupom(@Payload String cupom,
                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                              @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                              @Header(KafkaHeaders.OFFSET) Long offset) throws JsonProcessingException, RegraDeNegocioException  {
        TopicoCupomDTO topicoCupomDTO = objectMapper.readValue(cupom, TopicoCupomDTO.class) ;
        log.info("CUPOM RECEBIDO -> '{}' Nome -> '{}' Valor -> '{}' -> Data de Vencimento -> '{}' Partition -> '{}' ", topicoCupomDTO.getNome(), topicoCupomDTO.getPreco(),
                topicoCupomDTO.getDataVencimento(), partition);
        cupomRepository.save(objectMapper.convertValue(topicoCupomDTO, CupomEntity.class));
        cupomGold(topicoCupomDTO);
    }

    public void cupomGold(TopicoCupomDTO topicoCupomDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioGold = usuarioService.findUserByEmail(topicoCupomDTO.getEmail());
        if(usuarioGold != null){
            usuarioLoginService.atualizarCargo(usuarioGold.getIdUsuario());
        }
    }


}
