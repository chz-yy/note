# spring 监控

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

```yaml
server:
  port: 8081
  servlet:
    context-path: /leave-attendance
  tomcat:
    mbeanregistry:
      enabled: true
management:
  endpoints:
    web:
      exposure:
        include: prometheus  # 打开 Prometheus 的 Web 访问 Path
  metrics:
    # 在 Prometheus 中添加特别的 Labels
    # 必须加上对应的应用名，因为需要以应用的维度来查看对应的监控
    tags:
      application: Leave-Attendance
    # 下面选项建议打开，以监控 http 请求的 P99/P95 等，具体的时间分布可以根据实际情况设置
    distribution:
      sla:
        http:
          server:
            requests: 1ms,5ms,10ms,50ms,100ms,200ms,500ms,1s,5s
      percentiles-histogram:
        http:
          server:
            requests: true # 开启 http server 的请求监控
```

[Prometheus 监控服务 Spring Boot 接入-接入指南-文档中心-腾讯云 (tencent.com)](https://cloud.tencent.com/document/product/1416/56031)
