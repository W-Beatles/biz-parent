# biz-boot-starter-sequence

### 项目介绍
基于`zookeeper`和`snowflake`算法的分布式id生成器

### 使用方法

1. 添加pom依赖
    ```
    <dependency>
        <groupId>cn.waynechu</groupId>
        <artifactId>biz-boot-starter-sequence</artifactId>
    </dependency>
    ```
2. 添加配置
    ```
    sequence.enable=true
    sequence.group=default
    sequence.strategy=snowflake
    sequence.registryFile=snowflake
    sequence.zookeeper.serverLists=localhost:2181,localhost:2182
    sequence.zookeeper.namespace=sequence
    ```
2. 注入`IdGenerator`对象
    ```
    @Autowire
    private IdGenerator generator;
    ```
3. 生成分布式id
    ```
    long id = generator.nextId();
    long[] ids = generator.nextIds();
    String idStr = generator.nextStringId();
    ...
    ```
   
### 其他解决方案
1. 基于UUID
2. Redis分布式自增ID
3. 百度UID-Generator，支持雪花ID模式。https://github.com/baidu/uid-generator
4. 美团Leaf分布式ID生成器，支持号段模式和雪花ID模式。https://github.com/Meituan-Dianping/Leaf

