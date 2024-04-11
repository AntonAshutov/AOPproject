package com.example.aopproject.aspect;

import com.example.aopproject.service.MethodExecutionTimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TrackAsyncTimeAspect {

    private final MethodExecutionTimeService executionTimeService;

    @Around("@annotation(com.example.aopproject.annotation.TrackAsyncTime)")
    public Object trackAsyncTime(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("method {} started asynchronously", proceedingJoinPoint.getSignature());
        double startTime = (double) System.currentTimeMillis();
        CompletableFuture<Object> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                log.error("Error in trackAsyncTime aspect", throwable);
                return null;
            }
        });

        Object result = completableFuture.join();
        Double executionTime = System.currentTimeMillis() - startTime;
        log.info("{} executed asynchronously in {} ms, result - {}"
                , proceedingJoinPoint.getSignature(), executionTime, result);

        executionTimeService.saveMethodExecutionTime(proceedingJoinPoint.getSignature().toString(), executionTime);

        return result;
    }
}
