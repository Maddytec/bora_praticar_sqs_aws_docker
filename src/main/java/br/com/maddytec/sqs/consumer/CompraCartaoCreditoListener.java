package br.com.maddytec.sqs.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class CompraCartaoCreditoListener {

	private static final Logger logger = LoggerFactory.getLogger(CompraCartaoCreditoListener.class);

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Value("${cloud.aws.fila.compra_cartao_credito_aprovada}")
	private String uriCompraCartaoCreditoAprovada;

	@SqsListener(value =  "${cloud.aws.fila.compra_cartao_credito}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void recieveMessage(String message) throws Exception {

		logger.info("Message do SQS Queue - Cartão de Credito - {}", message);

		// ...

		envioMensagemFilaNovaSolicitacaoInconsistenteSalesForceRecebida(message);
	}


	public void envioMensagemFilaNovaSolicitacaoInconsistenteSalesForceRecebida(String mensagem ){
		queueMessagingTemplate.send(uriCompraCartaoCreditoAprovada, MessageBuilder.withPayload(mensagem).build());
	}
	
}