package com.delete.blogg.common.aop;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;
//type表示可以放在类上面，method表示可以放在方法上
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface LogAnnotation {
    String module() default "";
    String operator() default "";
}
