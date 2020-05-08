# biz-spring-cloud-oauth-server

统一认证授权服务

### OAuth2 角色

- 资源所有者(Resource Owner) - 用户
- 客户端 (Client) - 第三方应用
- 授权服务器 (Authorization Server） - 对客户端发送的请求信息进行验证并返回token的服务器
- 资源服务器 (Resource Server） - 提供用户资源的服务器

### OAuth2 4种授权模式
- `authorization_code` 授权码模式：是功能最完整、流程最严密的授权模式。它的特点就是通过客户端服务器与服务端服务器交互，常见的第三方平台登录功能基本使用这种模式
- `implicit` 简化模式：不需要客户端服务器参与，直接通过浏览器向授权服务器申请令牌
- `password` 密码模式：用户将账号和密码直接告诉第三方客户端，客户端使用这些信息向授权服务器申请令牌（需要用户对客户端高度信任）
- `client_credentials` 客户端模式：客户端使用自身向授权服务器申请授权，不需要用户参与

### Oauth2 Token

token基本内容如下

* access_token：表示访问令牌，必选项
* token_type：表示令牌类型，该值大小写不敏感，必选项，可以是Bearer类型或其它类型
* expires_in：表示过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间
* refresh_token：表示更新令牌，用来获取下一次的访问令牌，可选项
* scope：表示权限范围，如果与客户端申请的范围一致，此项可省略

**密码模式使用的例子**

以某App登陆为例，用户请求获取授权信息

```
+-----------+                                     +-------------+
|           |       1-Request Authorization       |             |
|           |------------------------------------>|             |
|           |     grant_type&username&password    |             |--+
|           |                                     |Authorization|  | 2-Gen
|  Client   |                                     |Service      |  |   JWT
|           |       3-Response Authorization      |             |<-+
|           |<------------------------------------| Private Key |
|           |    access_token / refresh_token     |             |
|           |    token_type / expire_in / jti     |             |
+-----------+                                     +-------------+
```

### Spring OAuth2 + JWT Token简介

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

| 表名                     |   简介                        |           说明                       |
|-------------------------|-------------------------------|-----------------------------------------|
| oauth_client_details    |   client持久化表               |                                          |
| oauth_client_token      |   用户客户端存储从服务端获取的token|                                         |
| oauth_access_token      |   access_token的持久表          |                                         |
| oauth_refresh_token     |   refresh_token的持久化表       |                                          |
| oauth_approvals         |   授权码模式授权信息持久化表      |  用户授权记录                             |
| oauth_code              |   授权码模式code持久化表         |  code临时存放，code使用过就删除            |

具体表结构请参考[spring-oauth-server 数据库表说明](http://andaily.com/spring-oauth-server/db_table_description.html)

#### 用户角色资源等表结构

| 表名        |   简介        |  备注                    |
|------------|---------------|-------------------------|
| users      |   用户表       |  使用应用的用户           |
| groups     |   组织表       |  通过user_group_relation与users关联，多对多     |
| position   |   岗位表       |  通过user_position_relation与users关联，多对多  |
| roles      |   角色表       |  通过user_role_relation与users关联，多对多      |
| menu       |   菜单表       |  通过role_menu_relation与roles关联，多对多      |
| resource   |   资源表       |  通过role_resource_relation与roles关联，多对多  |