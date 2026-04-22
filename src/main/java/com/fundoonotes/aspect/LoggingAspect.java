package com.fundoonotes.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging execution of service methods.
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.fundoonotes.service.impl..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("AOP START: {}() with args: {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.fundoonotes.service.impl..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("AOP SUCCESS: {}() returned: {}", joinPoint.getSignature().getName(), result != null ? result.toString() : "null");
    }

    @AfterThrowing(pointcut = "execution(* com.fundoonotes.service.impl..*(..))", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("AOP ERROR: {}() threw exception: {}", joinPoint.getSignature().getName(), e.getMessage());
    }
}
