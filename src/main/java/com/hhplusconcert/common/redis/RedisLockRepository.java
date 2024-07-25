package com.hhplusconcert.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {
    private final RedisTemplate<String, String> lockRedisTemplate;

    public Boolean lock(String methodName, String userId) {
        String lockKey = lockKeyGen(methodName, userId);

        return lockRedisTemplate.opsForValue()
                .setIfAbsent(lockKey, "lock", Duration.ofMillis(3000L));
    }

    public Boolean unlock(String methodName, String userId) {
        return lockRedisTemplate.delete(lockKeyGen(methodName, userId));
    }

    private String lockKeyGen(String methodName, String userId) {
        return methodName + ":" + userId;
    }
}
