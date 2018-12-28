package cn.waynechu.appender.rabbitmq.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.rabbitmq.client.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author zhuwei
 * @date 2018/12/27 18:55
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class RabbitmqAppender extends AppenderBase<ILoggingEvent> {
    private JsonLayout layout = new JsonLayout();

    private ConnectionFactory factory = new ConnectionFactory();
    private Connection connection = null;

    private String host = "localhost";
    private int port = 5762;
    private String username = "guest";
    private String password;
    private String virtualHost = "/";

    private Channel channel = null;

    /**
     * Name of the exchange to publish log events to.
     */
    private String exchange = "logs";

    /**
     * Type of the exchange to publish log events to.
     */
    private String type = "topic";
    private boolean exchangeDurable = true;
    private boolean queueDurable = true;

    private String queue = "logback";
    private String routingKey = "logback";

    private String applicationId = "anonymous";
    private String machineName = "Undefined";
    private String clientProvidedName = "Undefined";

    private ExecutorService senderPool;

    private int corePoolSize = 0;
    private int maxPoolSize = Integer.MAX_VALUE;
    private int keepAliveSeconds = 60;

    @Override
    protected void append(final ILoggingEvent iLoggingEvent) {
        senderPool.execute(() -> {
            String payload = layout.format(iLoggingEvent);
            try {
                String id = String.format("%s:%s", applicationId, System.currentTimeMillis());

                AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
                builder.appId(applicationId)
                        .correlationId(id)
                        // 持久化要发送的消息
                        .deliveryMode(2)
                        .contentType("text/json")
                        .contentEncoding("UTF-8")
                        .type(iLoggingEvent.getLevel().toString());
                this.channel.basicPublish(exchange, routingKey + "." + iLoggingEvent.getLevel().toString().toLowerCase(), builder.build(), payload.getBytes());
            } catch (IOException e) {
                log.error("同步日志到rabbitmq异常，payload = {}", payload, e);
                reConnection();
            }
        });
    }

    @Override
    public void start() {
        super.start();
        init();
    }

    private void init() {
        try {
            this.machineName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("rabbitmq-appender获取机器名称异常", e);
            throw new RuntimeException(e);
        }

        //== creating connection
        this.createConnection();

        //== creating channel
        this.createChannel();

        //== create exchange
        this.createExchange();

        //== create queue
        this.createQueue();

        senderPool = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                runnable -> {
                    Thread result = new Thread(runnable);
                    result.setName("RABBITMQ-APPENDER");
                    return result;
                }
        );
    }

    /**
     * 重新连接rabbit
     */
    private void reConnection() {
        synchronized (this) {
            if (this.connection != null && this.connection.isOpen() && this.channel != null && this.channel.isOpen()) {
                return;
            }
            if (this.connection == null || !this.connection.isOpen()) {
                this.createConnection();
            }
            this.createChannel();
            this.createExchange();
            this.createQueue();
        }
    }

    /**
     * Sets the ConnectionFactory parameters
     */
    private void setFactoryConfiguration() {
        factory.setHost(this.host);
        factory.setPort(this.port);
        factory.setVirtualHost(this.virtualHost);
        factory.setUsername(this.username);
        Optional.ofNullable(this.password).ifPresent(factory::setPassword);
    }

    /**
     * Creates connection to RabbitMQ server according to properties
     */
    private void createConnection() {
        this.setFactoryConfiguration();
        if (this.connection == null || !this.connection.isOpen()) {
            Address address = new Address(this.host, this.port);
            List<Address> addresses = new ArrayList<>();
            addresses.add(address);
            try {
                this.connection = factory.newConnection(addresses, this.machineName + "|" + this.clientProvidedName);
            } catch (IOException e) {
                log.error("rabbitmq-appender创建连接IO异常", e);
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                log.error("rabbitmq-appender创建连接超时", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates channel on RabbitMQ server
     */
    private void createChannel() {
        if (this.connection != null && this.connection.isOpen() && this.channel == null) {
            try {
                this.channel = this.connection.createChannel();
            } catch (IOException e) {
                log.error("rabbitmq-appender 创建ChannelIO异常", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Declares the exchange on RabbitMQ server according to properties set
     */
    private void createExchange() {
        if (this.channel != null && this.channel.isOpen()) {
            synchronized (this.channel) {
                try {
                    this.channel.exchangeDeclare(this.exchange, this.type, this.exchangeDurable);
                } catch (IOException e) {
                    log.error("rabbitmq-appender声明exchangeIO异常");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Declares and binds queue on rabbitMQ server according to properties
     */
    private void createQueue() {
        if (this.channel != null && this.channel.isOpen()) {
            synchronized (this.channel) {
                try {
                    this.channel.queueDeclare(this.queue, this.queueDurable, false, false, null);
                } catch (IOException e) {
                    log.error("rabbitmq-appender声明queueIO异常");
                    throw new RuntimeException(e);
                }
                try {
                    this.channel.queueBind(this.queue, this.exchange, this.routingKey);
                } catch (IOException e) {
                    log.error("rabbitmq-appender绑定queueIO异常");
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
