## biz-spring-cloud-eureka

## 启用HTTPS

1. 证书生成  

    生成Server端证书:  
    ```
    keytool -genkeypair -alias server -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore server.p12 -validity 3650
    ```
    会在当前目录下生成一个名为server.p12的文件。  
    
    生成Client端证书:   
    ```
    keytool -genkeypair -alias client -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore client.p12 -validity 3650
    ```
    
    导出两个p12的证书:  
    ```
    keytool -export -alias server -file server.crt --keystore server.p12
    keytool -export -alias client -file client.crt --keystore client.p12
    ```
    
    接下来将server.crt导入client.p12密钥库中，使Client端信任Server的证书(这里要输入的是Client的密钥):  
    ```
    keytool -import -alias server -file server.crt -keystore client.p12
    ```
    
    然后将client.crt文件server.p12中，使Server信任Client的证书(这里要输入的是Server的密钥):  
    ```
    keytool -import -alias client -file client.crt -keystore server.p12
    ```
    
2. Eureka Server配置

    把生成的server.p12放到工程的resources目录下，然后指定相关配置如下:  
    ```
    server:
      port: 9001
      ssl:
        enabled: true
        key-store: classpath:server.p12
        key-store-password: 123456
        key-store-type: PKCS12
        key-alias: server
    
    spring:
      application:
        name: eureka-server
      security:
        user:
          name: admin
          password: 123456
    
    eureka:
      instance:
        hostname: localhost
        # 使用IP注册
        prefer-ip-address: true
        # Client向Eureka Server发送心跳的时间间隔，默认30s
        lease-renewal-interval-in-seconds: 30
        # 服务失效剔除时间，默认90秒
        lease-expiration-duration-in-seconds: 90
        # ssl配置
        secure-port: ${server.port}
        secure-port-enabled: true
        non-secure-port-enabled: false
        home-page-url: https://${eureka.instance.hostname}:${server.port}/
        status-page-url: https://${eureka.instance.hostname}:${server.port}/
      client:
        # 是否将该当前实例注册到Eureka Server
        register-with-eureka: true
        # 是否获取注册表信息
        fetch-registry: true
        service-url:
          defaultZone: https://${spring.security.user.name}:${spring.security.user.password}@localhost:9002/eureka/
        # 拉取注册信息间隔。默认30 (测试环境，可适当降低该值来保证及时获取注册信息)
        registry-fetch-interval-seconds: 5
      server:
        # 是否开启自我保护模式。默认开启 (可能由于网络抖动造成Client向Server发送心跳失败，但服务实例是健康的。
        # 当出现大范围误判，可能导致注册的服务发范围被剔除。SELF PRESERVATION机制保证当一分钟内收到的续约次数小于指定伐值，
        # 则禁止定时任务剔除失效实例。开发测试环境，开启该机制反而可能会影响系统的持续集成)
        enable-self-preservation: true
        # Eureka服务器获取不到集群里对等服务器上的实例时，多久不对外提供注册服务
        wait-time-in-ms-when-sync-empty: 0
        # EvictionTack服务剔除定时任务间隔。默认60s(测试环境可适当降低该值来保证服务及时剔除)
        eviction-interval-timer-in-ms: 60000
        # (关闭Server的response cache，或者修改response cache的过期时间，可有效解决Server中存留过期实例信息的问题)
        use-read-only-response-cache: false
        # 修改response cache的过期时间
        response-cache-auto-expiration-in-seconds: 60
    ```