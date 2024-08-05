package com.hhplusconcert.infra.waitingQueue.impl;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.repository.WaitingQueueRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class WaitingQueueRedisRepositoryImpl implements WaitingQueueRepository {
    //
    private final ZSetOperations<String, String> zSetOperations;
    @Value("${concert.queueKey}")
    private String waitingQueueKey;

    public WaitingQueueRedisRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        //
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    @Override
    public boolean save(WaitingQueue waitingQueue) {
        // FIXME TTL 적용
        return Boolean.TRUE.equals(zSetOperations.add(this.waitingQueueKey, waitingQueue.getKey().toString(), waitingQueue.getCreateAt()));
    }

    @Override
    public Long findWaitingQueueCount(WaitingQueue.WaitingQueueKey key) {
        return this.zSetOperations.rank(waitingQueueKey, key.toString());
    }

    @Override
    public List<WaitingQueue.WaitingQueueKey> findWaitingQueuesByJoinCount(Long joinCount) {
        //
        Set<String> keys = this.zSetOperations.range(waitingQueueKey, 0, joinCount > 0 ? joinCount - 1 : joinCount);
        return keys != null ? keys.stream().map(WaitingQueue.WaitingQueueKey::new).toList() : List.of();
    }

    @Override
    public boolean existWaitingQueue(WaitingQueue.WaitingQueueKey key) {
        Double score = this.zSetOperations.score(waitingQueueKey, key.toString());
        return score != null;
    }

    @Override
    public void deleteWaitingQueuesByRange(long endRange) {
        //
        this.zSetOperations.removeRange(waitingQueueKey, 0, endRange);
    }
}
