## skywalking agent基础镜像

1. 添加`skywalking-apm agent`到此文件夹
2. 构建镜像
    ```
    docker build -t waynechu/java-skywalking:8.4.0 .
    ```
3. 推送远程仓库
    ```
    docker push waynechu/java-skywalking:8.4.0
    ```