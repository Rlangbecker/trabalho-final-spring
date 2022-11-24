package com.vemser.geekers.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.TopicoCupomDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutorService {

    @Value(value = "${kafka.topic}")
    private String topic;
    private String usuario = "KAIO";
    private static final Integer PARTICAO = 0; // nossa partição.

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void enviarMensagem(TopicoCupomDTO topicoCupomDTO) throws JsonProcessingException {
        String mensagemStr = objectMapper.writeValueAsString(topicoCupomDTO);

            // mensagem, chave, topico
            MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagemStr)
                    .setHeader(KafkaHeaders.TOPIC, topic)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                    .setHeader(KafkaHeaders.PARTITION_ID, PARTICAO) // NÃO É OBRIGATÓRIO TER UM ENUM, PODE SER SOMENTE O NUMERO
                    // COMEÇA POR ZERO (0)
                    ;

            Message<String> message = stringMessageBuilder.build();

            ListenableFuture<SendResult<String, String>> enviadoParaTopico = kafkaTemplate.send(message);
            enviadoParaTopico.addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onSuccess(SendResult result) {
                    log.info(" Log enviado para o kafka com o texto: {} ", topicoCupomDTO.getNome());
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error(" Erro ao publicar duvida no kafka com a mensagem: {}", topicoCupomDTO.getNome(), ex);
                }
            });

    }
}
