package com.wuyun.annotation;

import com.wuyun.validator.CommentTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * 评论类型注解
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@Documented
@Constraint(validatedBy = {CommentTypeValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommentType {

    String message() default "{javax.validation.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return 评论类型
     */
    int[] values() default {};

}