package com.ccx.jsj.commons.aspect.weblog;

import java.lang.annotation.*;

/**
 * 日志注解
 * 注解到方法上 该方法会进入到切面
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String operation() default "";

}
