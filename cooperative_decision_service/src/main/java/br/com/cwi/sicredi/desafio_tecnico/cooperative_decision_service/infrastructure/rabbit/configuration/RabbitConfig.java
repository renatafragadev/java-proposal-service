package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.rabbit.configuration;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.rabbit.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Bean
    public FanoutExchange sessionFanoutExchange() {
        return new FanoutExchange(RabbitConstant.SESSION_FANOUT_EXCHANGE, true, false);
    }

    @Bean
    public Queue cooperativeReportQueue() {
        return new Queue(RabbitConstant.SESSION_COOPERATIVE_REPORT_QUEUE, true);
    }

    @Bean
    public Queue humanResourceQueue() {
        return new Queue(RabbitConstant.SESSION_HUMAN_RESOURCE_QUEUE, true);
    }

    @Bean
    public Binding cooperativeReportBinding() {
        return BindingBuilder.bind(cooperativeReportQueue()).to(sessionFanoutExchange());
    }

    @Bean
    public Binding humanResourceBinding() {
        return BindingBuilder.bind(humanResourceQueue()).to(sessionFanoutExchange());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        return rabbitTemplate;
    }
}
