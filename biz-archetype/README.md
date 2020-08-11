# biz-spring-cloud-archetype

### 项目介绍
1. 用于快速生成SpringCloud项目开发骨架，不用再考虑框架的搭建及相关组件的集成，实现敏捷开发
2. `biz-archetype-template`基础项目骨架模型jar包可deploy到公司的maven库中，然后使用脚本构建项目骨架原型
3. 同时提供一键生成项目骨架原型页面，可以通过页面上的简单几项配置即可生成项目骨架原型，方便快捷
4. 如需自定义项目开发骨架，在`biz-archetype-template`下添加项目模板即可

### 项目骨架原型生成

#### 方式一: 脚本生成
1. 进入`biz-archetype/biz-archetype-portal/biz-archetype-portal-api/src/main/resources/CreateProject.bat`目录
2. 执行命令 `CreateProject.bat 项目名 包名`

例如:
```shell
(Windows) CreateProject.bat biz-spring-cloud-service-order order
(Linux) CreateProject.sh -n biz-spring-cloud-service-order -p order
```

即可生成项目骨架原型 `biz-spring-cloud-service-order`，并且包名为 `cn.waynechu.order`

### 方式二: 页面生成（推荐）
1. 启动`biz-archetype-portal`后端项目
2. 启动`biz-archetype-portal-ui`前端项目
3. 在页面上配置项目信息即可生成并下载项目骨架原型