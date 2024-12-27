# spring cloud

## 服务发现

```xml
//nacos服务注册
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>

//依赖管理
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-dependencies</artifactId>
    <version>${spring-cloud.version}</version> <!-- 替换为您需要的版本 -->
    <type>pom</type>
    <scope>import</scope>
</dependency>

<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
    <version>${spring-cloud-alibaba.version}</version> <!-- 替换为您需要的版本 -->
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/wesay?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8
    username: root
    password: 12345678
    driver-class-name: com.mysql.cj.jdbc.Driver
  cloud:  //服务注册
    nacos:
      discovery:
        service: wesay  # 确保该值不为空
        server-addr: 58.199.160.214:8848
```

## 网关配置

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

```yaml
server:
  port: 8080
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 58.199.160.214:8848
        service: gateway
    gateway:
      globalcors:
        cors-configurations:
          '[/**]':
            allowedOrigins: "*"
            allowedHeaders: "*"
            allowedMethods: "*"
            maxAge: 3600
      default-filters:
        - DedupeResponseHeader=Vary Access-Control-Allow-Origin Access-Control-Allow-Credentials, RETAIN_FIRST
      routes:
        - id: wesay
          uri: lb://wesay
          predicates:
            - Path=/api/**
```

