package com.hand.xy99.util.operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationRecord {

    /**
     * 操作类型，默认提交订单
     */
    String operateType() default "0";

    /**
     * 订单号字段名，默认salesOrderNumber
     */
    String salesOrderNumber() default "salesOrderNumber";
    /**
     * 订单状态
     */
    String  orderStatus() default "0";

}
