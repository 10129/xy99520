package com.hand.xy99.util.operation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;


@Aspect
@Component
public class Operation {


    Logger logger = LoggerFactory.getLogger(Operation.class);

    ThreadLocal<String> localOperation = new ThreadLocal<>();

    ThreadLocal<String> localOrderNumber = new ThreadLocal<>();
    private Object object;


    @Pointcut("execution(@com.hand.xy99.util.operation.OperationRecord * *(..))")
    public void record() {
    }

    @Around("record()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Around");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        OperationRecord operationRecord = methodSignature.getMethod().getAnnotation(OperationRecord.class);
        String operateType = operationRecord.operateType();
        String salesOrderNumber = operationRecord.salesOrderNumber();
        String orderStatus= operationRecord.orderStatus();
        localOperation.set(operateType);
        localOrderNumber.set(salesOrderNumber);
        return joinPoint.proceed();
    }

    @AfterReturning(pointcut = "record()", returning = "result")
    public void after(Object result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //返回发出请求的IP地址
            String ip = httpServletRequest.getRemoteAddr();
//            if (result instanceof ResponseData) {
//                List list = ((ResponseData) result).getRows();
//
//            } else if (result instanceof List) {
//                List list = (List) result;
//
//            } else if (request instanceof BaseDTO) {
                Class clazz = request.getClass();
                PropertyDescriptor descriptor = new PropertyDescriptor(localOrderNumber.get(), clazz);
                Method method = descriptor.getReadMethod();
                Object salesOrderNumber = method.invoke(request);

//            } else {
//                logger.error("不能解析的返回值");
//            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        System.out.println(result.toString());
        logger.info("process after");
    }

}
