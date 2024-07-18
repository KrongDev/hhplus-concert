package com.hhplusconcert.common.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueueCheckAnnotation {
    // 추후 상세 체크 권한 추가 가능
}
