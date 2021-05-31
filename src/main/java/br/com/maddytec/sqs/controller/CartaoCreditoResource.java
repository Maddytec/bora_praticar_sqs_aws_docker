package br.com.maddytec.sqs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartao")
public class CartaoCreditoResource {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.fila.compra_cartao_credito}")
    private String uriCompraCartaoCredito;

    @PostMapping
    public ResponseEntity<String> consultaCartaoCredito(@RequestBody String mensagem){
        queueMessagingTemplate.send(uriCompraCartaoCredito, MessageBuilder.withPayload(mensagem).build());
        return ResponseEntity.ok("Solicitação enviada com sucesso.");
    }
}
