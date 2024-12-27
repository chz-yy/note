# 异步Servlet

```
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public ThreadPoolTaskExecutor webAsyncThreadPoolExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.setKeepAliveSeconds(3);
        return threadPoolTaskExecutor;
    }
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer){
        configurer.setDefaultTimeout(3000);
        configurer.setTaskExecutor(webAsyncThreadPoolExecutor());
    }
    @Bean
    public TimeoutCallableProcessingInterceptor timeoutCallableProcessingInterceptor(){
        return new TimeoutCallableProcessingInterceptor();
    }
}
```

## use

```java
@RequireLogin
    @PostMapping("/doSeckill")
    public Callable<Result<?>>  doSeckill(long seckillId, Integer time, @RequestUser UserInfo userInfo){
        return ()->{}
    }
```

