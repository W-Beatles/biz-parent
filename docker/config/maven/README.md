## skywalking agent & maven基础镜像

1. 添加`skywalking-apm agent`到此文件夹
2. 添加`apache-maven-3.5.4`到此文件夹
3. 构建镜像
    ```
    docker build -t waynechu/java-skywalking-maven .
    ```