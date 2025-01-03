# lua脚本

### 前置准备

#### 依赖

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-redis</artifactId>
	<version>2.2.5.RELEASE</version>
</dependency>
```

**脚本**

```lua
-- string 的key
local stringKey = KEYS[1]
-- 对value的变动补偿， 可以为负数
local step = tonumber(ARGV[1])
-- 过期时间
local expireAt = tonumber(ARGV[2])
-- check 值是否已存在, 不存在先插入key，并初始化值
local keyExist = redis.call("EXISTS", KEYS[1]);
if (keyExist < 1) then
    redis.call("SET", KEYS[1], 0)
    -- 设置过期时间
    redis.call("EXPIREAT", KEYS[1], expireAt)
end
-- 做递增或递减操作
redis.call("INCRBY", KEYS[1], step)

-- 返回最新结果，由于使用 stringRedisTemplate，返回值用string，否则值转换有问题
return tostring(redis.call("GET", KEYS[1]))

```

config

```java
@Configuration
public class CacheConfig {    
	@Bean
    public RedisScript<Boolean> redisScript(){
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
        script.setResultType(Boolean.class);
        script.setLocation(new ClassPathResource("META-INF/scripts/redis_lock_lua.lua"));
        return script;
    }
}
```

use

```java
ret = redisTemplate.execute(redisScript, Collections.singletonList(key), threadId, timeout+"");
```

