package com.example.aopproject.aspect;

import com.example.aopproject.service.MethodExecutionTimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TrackTimeAspect {

    private final MethodExecutionTimeService executionTimeService;

    @Around("@annotation(com.example.aopproject.annotation.TrackTime)")
    public Object trackTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("{} method started", proceedingJoinPoint.getSignature());
        double startTime = (double) System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        Double executionTime = System.currentTimeMillis() - startTime;
        log.info("{} executed in {} ms", proceedingJoinPoint.getSignature(), executionTime);

        executionTimeService.saveMethodExecutionTime(proceedingJoinPoint.getSignature().toString(), executionTime);
        return result;
    }

}
