package com.wuyun.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author DarkClouds
 * @date 2023/05/12
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OptLogger {

    /**
     * @return 操作描述
     */
    String value() default "";

}
