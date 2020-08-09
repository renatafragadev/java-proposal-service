package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.messaging.component;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.rabbit.constant.RabbitConstant;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.integration.messaging.pojo.CountingVoteMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CountingVoteMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void send(CountingVoteMessage countingVoteMessage) {
        log.info("Producer - send | countingVoteMessage: {}", countingVoteMessage);

        rabbitTemplate.setExchange(RabbitConstant.SESSION_FANOUT_EXCHANGE);
        rabbitTemplate.convertAndSend(countingVoteMessage);
    }
}
