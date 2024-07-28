package com.hhplusconcert.interfaces.aop;

import com.hhplusconcert.common.annotation.RedissonLock;
import com.hhplusconcert.common.redis.RedisLockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedissonLockAspect {
    //
    private final RedisLockRepository redisLockRepository;
    private final RedissonClient redissonClient;

//    @Around("@annotation(com.hhplusconcert.common.annotation.RedissonLock)")
//    public Object redissonSimpleLock(ProceedingJoinPoint joinPoint) throws Throwable {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        RedissonLock annotation = method.getAnnotation(RedissonLock.class);
//        String userId = "";
//        Object[] args = joinPoint.getArgs();
//        String[] parameterNames = signature.getParameterNames();
//        for (int i = 0; i < parameterNames.length; i++) {
//            if (parameterNames[i].equals(annotation.referenceKey())) {
//                userId = String.valueOf(args[i]);
//                break;
//            }
//        }
//
//        String lockKey = method.getName() + userId;
//        boolean lock = redisLockRepository.lock(method.getName(), userId);
//        try {
//            if (!lock) {
//                log.info("Lock 획득 실패={}", lockKey);
//                return null;
//            }
//            log.info("로직 수행");
//            return joinPoint.proceed();
//        } catch (InterruptedException e) {
//            log.info("에러 발생");
//            throw e;
//        } finally {
//            log.info("락 해제");
//            redisLockRepository.unlock(method.getName(), userId);
//        }
//    }

    @Around("@annotation(com.hhplusconcert.common.annotation.RedissonLock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock annotation = method.getAnnotation(RedissonLock.class);
        String referanceId = "";
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(annotation.referenceKey())) {
                referanceId = String.valueOf(args[i]);
                break;
            }
        }

        String lockKey = method.getName() + referanceId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean lockable = lock.tryLock(annotation.waitTime(), annotation.leaseTime(), TimeUnit.MILLISECONDS);
            if (!lockable) {
                log.info("Lock 획득 실패={}", lockKey);
                return null;
            }
            log.info("로직 수행");
            return joinPoint.proceed();
        } catch (InterruptedException e) {
            log.info("에러 발생");
            throw e;
        } finally {
            log.info("락 해제");
            lock.unlock();
        }
    }
}
