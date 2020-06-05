# biz-spring-cloud-oauth-server

统一认证授权服务

### OAuth2 简介

OAuth 2.0是用于授权的行业标准协议。OAuth 2.0为简化客户端开发提供了特定的授权流，包括Web应用、桌面应用、移动端应用等

### OAuth2 角色

- 资源所有者(Resource Owner) - 用户
- 客户端 (Client) - 第三方应用
- 授权服务器 (Authorization Server） - 对客户端发送的请求信息进行验证并返回token的服务器
- 资源服务器 (Resource Server） - 提供用户资源的服务器

### OAuth2 4种授权模式
- `authorization_code` 授权码模式：是功能最完整、流程最严密的授权模式。它的特点就是通过客户端服务器与服务端服务器交互，常见的第三方平台登录功能基本使用这种模式(标准方式)
- `implicit` 简化模式：不需要客户端服务器参与，直接通过浏览器向授权服务器申请令牌(为web浏览器应用设计)(不支持refresh token)
- `password` 密码模式：用户将账号和密码直接告诉第三方客户端，客户端使用这些信息向授权服务器申请令牌
- `client_credentials` 客户端模式：客户端使用自身向授权服务器申请授权，不需要用户参与 (不支持refresh token)

### Oauth2 Token

token基本内容如下：

- access_token：表示访问令牌，必选项
- token_type：表示令牌类型，该值大小写不敏感，必选项，可以是Bearer类型或其它类型
- expires_in：表示过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间
- refresh_token：表示更新令牌，用来获取下一次的访问令牌，可选项
- scope：表示权限范围，如果与客户端申请的范围一致，此项可省略

### Spring OAuth2 + JWT Token简介

#### Spring OAuth2默认提供的端点
- /oauth/authorize：授权端点
- /oauth/token：令牌端点
- /oauth/confirm_access：用户确认授权提交端点
- /oauth/error：授权服务错误信息端点
- /oauth/check_token：用于资源服务访问的令牌解析端点
- /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话

#### JWT(JSON Web Tokens)简介

>JWT是一种用于双方之间传递安全信息的简洁的、URL安全的表述性声明规范。JWT作为一个开放的标准（RFC 7519），定义了一种简洁的，自包含的方法用于通信双方之间以Json对象的形式安全的传递信息。因为数字签名的存在，这些信息是可信的，JWT可以使用HMAC算法或者是RSA的公私秘钥对进行签名。
>* 简洁(Compact): 可以通过URL，POST参数或者在HTTP header发送，因为数据量小，传输速度也很快。
>* 自包含(Self-contained)：负载中包含了所有用户所需要的信息，避免了多次查询数据库。

简短来说，用户请求时，将用户信息和授权范围序列化后放入一个JSON字符串，然后使用Base64进行编码，最终在授权服务器用私钥对这个字符串进行签名，得到一个JSON Web Token，我们可以像使用Access Token一样的直接使用它，假设其他所有的资源服务器都将持有一个RSA公钥。当资源服务器接收到这个在Http Header中存有Token的请求，资源服务器就可以拿到这个Token，并验证它是否使用正确的私钥签名（是否经过授权服务器签名，也就是验签）。验签通过，反序列化后就拿到OAuth2的验证信息。

* Jwt Token包含了使用.分隔的三部分

`{Header 头部}.{Payload 负载}.{Signature 签名}`

* Header 头部

JWT包含了使用.分隔的三部分： 通常包含了两部分，token类型和采用的加密算法
```
{
   "alg": "HS256",
   "typ": "JWT"
 }
```

* Payload 负载

Token的第二部分是负载，它包含了claim， Claim是一些实体（通常指的用户）的状态和额外的元数据。
```
{
   "user_name": "admin", 
   "scope": [
       "read"
   ], 
   "organization": "admin", 
   "exp": 1531975621, 
   "authorities": [
       "ADMIN"
   ], 
   "jti": "23408d38-8cdc-4460-beac-24c76dc7629a", 
   "client_id": "test_client"
}
```

* Signature 签名

使用Base64编码后的header和payload以及一个秘钥，使用header中指定签名算法进行签名。

Jwt Token例子：

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiXSwib3JnYW5pemF0aW9uIjoiYWRtaW4iLCJleHAiOjE1MzE5NzU2MjEsImF1dGhvcml0aWVzIjpbIkFETUlOIl0sImp0aSI6IjIzNDA4ZDM4LThjZGMtNDQ2MC1iZWFjLTI0Yzc2ZGM3NjI5YSIsImNsaWVudF9pZCI6InRlc3RfY2xpZW50In0.qawS1Z4j_h4vNx10GBC_Y_PHM1LLSQt64eniWLGzsJY
```
可到`http://www.bejson.com/enc/base64`解码，注意分3部分分别解，也可使用官网解码工具[官网解码](https://jwt.io/)

### 表结构简介

#### Spring OAuth2表结构

| 表名                     |   简介                        |           说明                           |
|-------------------------|-------------------------------|------------------------------------------|
| oauth_client_details    |   client持久化表               |                                          |
| oauth_client_token      |   用户客户端存储从服务端获取的token|                                        |
| oauth_access_token      |   access_token的持久表          |                                         |
| oauth_refresh_token     |   refresh_token的持久化表       |                                         |
| oauth_approvals         |   授权码模式授权信息持久化表      |  用户授权记录                            |
| oauth_code              |   授权码模式code持久化表         |  code临时存放，code使用过就删除           |

具体表结构请参考[spring-oauth-server 数据库表说明](http://andaily.com/spring-oauth-server/db_table_description.html)

#### 用户角色资源等表结构

| 表名        |   简介        |  备注                                          |
|------------|---------------|------------------------------------------------|
| user       |   用户表       |  使用应用的用户                                 |
| group      |   组织表       |  通过user_group_relation与user关联，多对多     |
| position   |   岗位表       |  通过user_position_relation与user关联，多对多  |
| role       |   角色表       |  通过user_role_relation与user关联，多对多      |
| menu       |   菜单表       |  通过role_menu_relation与role关联，多对多      |
| resource   |   资源表       |  通过role_resource_relation与role关联，多对多  |

### 授权码模式(authorization_code)

流程图：
![1.png](./docs/1.png "授权码模式流程图")  

1. 三方应用程序引导用户进入授权请求页，获取`authorization_code`:  
    ```
    http://localhost:9050/oauth/authorize?client_id=h5&response_type=code&scope=all&redirect_uri=https://www.baidu.com&state=home
    ```
   **注意**： 这个code只能用一次,如果这一次失败了则需要重新申请
   
2. 跳转回三方应用，三方应用需要校验`state`和请求时的是否一致：  
    ```
    https://www.baidu.com/?code=jqc8JE&state=home
    ```

3. 三方应用通过`code`换取`access_token`  
    ```
    POST http://localhost:9050/oauth/token
    ```
   
    ![2.png](./docs/2.png "授权码模式 - 获取token")

    返回结果：
    ```json
    {
        "access_token": "567750d0-ba45-4731-8d7a-37430c8b5600",
        "token_type": "bearer",
        "refresh_token": "c18d0cff-eb9a-43ec-b903-903f7451bc5d",
        "expires_in": 3548,
        "scope": "all"
    }
    ```

4. 刷新`access_token`  
    ```
    POST http://localhost:9050/oauth/token
    ```
   
    ![3.png](./docs/3.png "授权码模式 - 刷新token")

### 简化模式(implicit)

   ![4.png](./docs/4.png "简化模式")

1. 三方应用程序引导用户进入授权请求页，并请求获取`access_token`:  
    ```
    http://localhost:9050/oauth/authorize?client_id=web&response_type=token&scope=all&redirect_uri=https://www.baidu.com&state=home
    ```
   
2. 跳转回三方应用，三方应用需要校验`state`和请求时的是否一致：  
    ```
    https://www.baidu.com/#access_token=cdb00d51-b9c8-483a-ba09-2f7c37be664d&token_type=bearer&state=home&expires_in=43199
    ```

**说明**: 简化模式没有获取 code 的步骤，整个过程只传递了`client_id`，并没有传递`client_secret`，因而无法验证`client`的真实性。获得的
token 只有`access_token`没有`refresh token`

### 密码模式(password)

   ![5.png](./docs/5.png "密码模式")
   
1. 请求获取`access_token`：  
    ```
    POST http://localhost:9050/oauth/token?grant_type=password&client_id=android&client_secret=123456&scope=all&username=user&password=123456
    ```
   
    返回结果：
    ```json
    {
        "access_token": "ebdc9803-45a8-4f56-8f18-d1bcf65deaed",
        "token_type": "bearer",
        "expires_in": 2119,
        "scope": "all"
    }
    ```
   
   **说明**: 密码模式简单，但是授权服务器和三方应用必须有超高的信赖，否则密码容易泄露

### 客户端模式(client_credentials)

   ![6.png](./docs/6.png "客户端模式")
   
1. 请求获取`access_token`：  
   ```
   POST http://localhost:9050/oauth/token?grant_type=client_credentials&client_id=gateway&client_secret=123456&scope=api
   ```
   
   返回结果：
   ```json
    {
        "access_token": "0729d9bf-8683-43b6-a341-c52e9dd9623e",
        "token_type": "bearer",
        "expires_in": 3366,
        "scope": "api"
    }
   ```
   **说明**: 客户端模式不需要用户参与，所以token中拿不到用户信息

### 网关鉴权

![7.png](./docs/7.png "网关鉴权")