package com.hhplusconcert.infra.waitingQueue.impl;

import com.hhplusconcert.domain.waitingQueue.model.WaitingQueue;
import com.hhplusconcert.domain.waitingQueue.repository.WaitingQueueRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class WaitingQueueRedisRepositoryImpl implements WaitingQueueRepository {
    //
    private final ZSetOperations<String, String> zSetOperations;
    private final String waitingQueueKey = "concert-waiting-queue";

    public WaitingQueueRedisRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        //
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    @Override
    public boolean save(WaitingQueue waitingQueue) {
        //
        return Boolean.TRUE.equals(zSetOperations.add(this.waitingQueueKey, waitingQueue.getKey().toString(), waitingQueue.getCreateAt()));
    }

    @Override
    public Long findWaitingQueueCount(WaitingQueue.WaitingQueueKey key) {
        return this.zSetOperations.rank(waitingQueueKey, key.toString());
    }

    @Override
    public List<WaitingQueue.WaitingQueueKey> findWaitingQueuesByJoinCount(Long joinCount) {
        //
        Set<String> keys = this.zSetOperations.range(waitingQueueKey, 0, joinCount);
        return keys != null ? keys.stream().map(WaitingQueue.WaitingQueueKey::new).toList() : List.of();
    }

    @Override
    public boolean existWaitingQueue(WaitingQueue.WaitingQueueKey key) {
        Double score = this.zSetOperations.score(waitingQueueKey, key.toString());
        return score != null;
    }

    @Override
    public void deleteWaitingQueues(List<WaitingQueue.WaitingQueueKey> waitingQueueKeys) {
        //
        this.zSetOperations.remove(waitingQueueKey, waitingQueueKeys.stream().map(WaitingQueue.WaitingQueueKey::toString));
    }
}
