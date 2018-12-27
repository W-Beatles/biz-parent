package cn.waynechu.logback.appender.rabbitmq;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhuwei
 * @date 2018/12/27 18:55
 */
@Slf4j
public class RabbitMQAppender extends AppenderBase<ILoggingEvent> {private JSONLayout layout = new JSONLayout();

    private ConnectionFactory factory = new ConnectionFactory();
    private Connection connection = null;

    private String host = "localhost";
    private int port = 5762;
    private String username = "guest";
    private String password = "guest";
    private String virtualHost = "/";

    private Channel channel = null;

    private String exchange = "amqp-exchange";
    private String type = "topic";
    private boolean exchangeDurable = true;
    private boolean queueDurable = true;

    private String queue = "amqp-queue";
    private String routingKey = "";

    private String identifier="identifier";
    private String machineName = "Undefined";
    private String clientProvidedName = "Undefined";


    private ThreadPoolExecutor threadPoolExecutor;

    private int corePoolSize = 1;
    private int maxPoolSize = 3;
    private int keepAliveSeconds = 60;
    private int queueCapacity = 1000;

    @Override
    protected void append(final ILoggingEvent iLoggingEvent) {
        // TODO Auto-generated method stub

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String payload = layout.format(iLoggingEvent);
                try {
                    String id = String.format("%s:%s", identifier, System.currentTimeMillis());

                    AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
                    builder.appId(identifier)
                            .type(iLoggingEvent.getLevel().toString())
                            .correlationId(id)
                            .contentType("text/json");
                    createChannel().basicPublish(exchange, routingKey + "." + iLoggingEvent.getLevel().toString().toLowerCase(), builder.build(), payload.toString().getBytes());
                } catch (Exception e) {
                    log.error("同步日志到rabbitmq异常，忽略，payload={}", payload, e);
                    reCon();
                }
            }
        });
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        super.start();
        init();
    }

    public void init(){
        //== creating connection
        this.createConnection();

        //== creating channel
        this.createChannel();

        //== create exchange
        this.createExchange();

        //== create queue
        this.createQueue();

        try {
            machineName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("rabbitmq-appender获取机器名称异常", e);
            throw new RuntimeException(e);
        }
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueCapacity));
        threadPoolExecutor.setThreadFactory(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread result = new Thread(r);
                result.setName("RABBITMQ-APPENDER");
                return result;
            }
        });
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 重新连接rabbit
     */
    public void reCon(){
        synchronized (this) {
            if(this.connection != null && this.connection.isOpen() && this.channel != null && this.channel.isOpen())
                return;
            if(this.connection == null || !this.connection.isOpen()){
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
        factory.setPassword(this.password);
    }

    /**
     * Creates connection to RabbitMQ server according to properties
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    private Connection createConnection(){
        setFactoryConfiguration();
        if (this.connection == null || !this.connection.isOpen()) {
            Address address = new Address(this.host, this.port);
            List<Address> addrs = new ArrayList<>();
            addrs.add(address);
            try {
                this.connection = factory.newConnection(addrs, this.machineName + "|" + this.clientProvidedName);
            } catch (IOException e) {
                log.error("rabbitmq-appender创建连接IO异常", e);
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                log.error("rabbitmq-appender创建连接超时", e);
                throw new RuntimeException(e);
            }
        }

        return this.connection;
    }

    /**
     * Creates channel on RabbitMQ server
     * @return
     * @throws IOException
     */
    private Channel createChannel() {
        if (this.channel == null || !this.channel.isOpen() && (this.connection != null && this.connection.isOpen()) ) {
            try {
                this.channel = this.connection.createChannel();
            } catch (IOException e) {
                log.error("rabbitmq-appender创建ChannelIO异常", e);
                throw new RuntimeException(e);
            }
        }
        return this.channel;
    }

    /**
     * Declares the exchange on RabbitMQ server according to properties set
     * @throws IOException
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
     * @throws IOException
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
                    this.channel.queueBind(this.queue, this.exchange, this.routingKey + ".#");
                } catch (IOException e) {
                    log.error("rabbitmq-appender绑定queueIO异常");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public JSONLayout getLayout() {
        return layout;
    }

    public void setLayout(JSONLayout layout) {
        if(layout != null)
            this.layout = layout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isExchangeDurable() {
        return exchangeDurable;
    }

    public void setExchangeDurable(boolean exchangeDurable) {
        this.exchangeDurable = exchangeDurable;
    }

    public boolean isQueueDurable() {
        return queueDurable;
    }

    public void setQueueDurable(boolean queueDurable) {
        this.queueDurable = queueDurable;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getClientProvidedName() {
        return clientProvidedName;
    }

    public void setClientProvidedName(String clientProvidedName) {
        this.clientProvidedName = clientProvidedName;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

}
