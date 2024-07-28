package com.hhplusconcert.interfaces.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogicLoggingAop {
    //
    private final String loggingTemplate =
            "\n======================================================\n" +
            "Class Name: {}\n" +
            "Method Name: {}\n" +
            "Params: {}\n" +
            "StartTime: {}\n" +
            "EndTime: {}\n" +
            "======================================================";

    @Around("@annotation(com.hhplusconcert.common.annotation.LoggingPoint)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String params = Arrays.toString(joinPoint.getArgs());
        LocalDateTime startTime = LocalDateTime.now();
        Object proceed = joinPoint.proceed();
        LocalDateTime endTime = LocalDateTime.now();
        log.info(loggingTemplate, className, methodName, params, startTime, endTime);
        return proceed;
    }
}
