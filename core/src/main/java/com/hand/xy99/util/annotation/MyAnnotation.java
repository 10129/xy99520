package com.hand.xy99.util.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

    /**
     * 获取参数中的数据
     * @return
     */
    String operateType() default "0";

}