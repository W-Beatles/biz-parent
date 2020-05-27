# biz-spring-cloud-archetype

### 项目介绍
1. 用于快速生成SpringCloud项目开发骨架，不用再考虑框架的搭建及相关组件的集成，实现敏捷开发
2. 该基础jar包可deploy到公司的maven库中，这样开发同学直接使用脚本即可构建项目基础骨架

### 使用方法
进入`../script/CreateProject-latest.bat`目录，执行命令 `CreateProject-latest.bat 项目名 包名`

例如:
```shell
CreateProject-latest.bat service-order order
```

即可生成工程 `biz-spring-cloud-api-service-order`，包名为 `com.waynechu.order`