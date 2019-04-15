package com.hand.xy99.util.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAnno {

    Logger logger = LoggerFactory.getLogger(MyAnno.class);

    ThreadLocal<String> operateTypeLocal = new ThreadLocal<>();

    @Pointcut(value = "execution(@com.hand.xy99.util.annotation.MyAnnotation * *(..))")
    public void annotation() {
    }

    @Around("annotation()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Around");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        MyAnnotation record = methodSignature.getMethod().getAnnotation(MyAnnotation.class);
        String operateType = record.operateType();

        operateTypeLocal.set(operateType);

        return joinPoint.proceed();
    }

    @AfterReturning(pointcut = "annotation()", returning = "result")
    public void after(Object result) {
       String aa= operateTypeLocal.get();
        logger.info("process after"+aa);
    }


}

