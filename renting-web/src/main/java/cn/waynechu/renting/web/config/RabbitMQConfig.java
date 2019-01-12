package cn.waynechu.renting.web.config;

import cn.waynechu.renting.web.consts.RabbitMQConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author waynechu
 * @date 2019-01-12 15:26
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(false);
        return rabbitAdmin;
    }

    @Bean
    DirectExchange directExchange(RabbitAdmin rabbitAdmin) {
        log.info("声明Direct交换机: {}", RabbitMQConst.Exchange.DIRECT_RENTING);
        DirectExchange directExchange = new DirectExchange(RabbitMQConst.Exchange.DIRECT_RENTING, true, true);
        rabbitAdmin.declareExchange(directExchange);
        return directExchange;
    }

    @Bean
    Queue directQueue(RabbitAdmin rabbitAdmin) {
        log.info("声明Direct队列: {}", RabbitMQConst.Queue.DIRECT_RENTING);
        Queue queue = new Queue(RabbitMQConst.Queue.DIRECT_RENTING, true, true, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Binding bindingDirectQueue(Queue directQueue, DirectExchange directExchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(directQueue).to(directExchange).with(RabbitMQConst.RoutingKey.DIRECT_RENTING);
        rabbitAdmin.declareBinding(binding);
        log.info("完成directExchange: {} 与directQueue: {} 的绑定", RabbitMQConst.Exchange.DIRECT_RENTING, RabbitMQConst.Queue.DIRECT_RENTING);
        return binding;
    }
}
