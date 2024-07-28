package com.hhplusconcert.common.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {
    String value(); // Lock의 이름 ( 메소드 명 )
    long waitTime() default 5000L; // Lock 획득을 시도하는 최대 시간 ( ms )
    long leaseTime() default 2000L; // Lock 획득 후, 점유하는 최대 시간 ( ms )
    String referenceKey() default "userId";
}
