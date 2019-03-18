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

/**
 * @author waynechu
 * @date 2019-01-12 15:26
 */
@Slf4j
//@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public DirectExchange directExchange() {
        log.info("声明Direct交换机: {}", RabbitMQConst.Exchange.DIRECT_RENTING);
        return new DirectExchange(RabbitMQConst.Exchange.DIRECT_RENTING);
    }

    @Bean
    public Queue directQueue() {
        log.info("声明Direct队列: {}", RabbitMQConst.Queue.DIRECT_RENTING);
        return new Queue(RabbitMQConst.Queue.DIRECT_RENTING);
    }

    @Bean
    public Binding bindingDirectQueue(Queue directQueue, DirectExchange directExchange) {
        log.info("完成 {} 与 {} 的绑定", RabbitMQConst.Exchange.DIRECT_RENTING, RabbitMQConst.Queue.DIRECT_RENTING);
        return BindingBuilder.bind(directQueue).to(directExchange).with(RabbitMQConst.RoutingKey.DIRECT_RENTING);
    }
}
