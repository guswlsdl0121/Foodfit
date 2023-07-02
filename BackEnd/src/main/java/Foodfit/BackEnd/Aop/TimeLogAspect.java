package Foodfit.BackEnd.Aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeLogAspect.class);

    @Around("@annotation(Foodfit.BackEnd.Aop.Annotations.TimeLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;
        LOGGER.info("{} executed in {} ms", joinPoint.getSignature().getName(), executionTime);

        return result;
    }
}
