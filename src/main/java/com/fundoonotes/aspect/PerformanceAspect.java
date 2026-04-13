package com.fundoonotes.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Around("execution(* com.fundoonotes.service.impl.NoteServiceImpl.*(..)) || " +
            "execution(* com.fundoonotes.service.impl.UserServiceImpl.*(..)) || " +
            "execution(* com.fundoonotes.service.ReminderService.*(..))")
    public Object profileMethod(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info("AOP PERFORMANCE: {}() executed in {} ms", pjp.getSignature().getName(), executionTime);
        return output;
    }
}
