package com.ice.framework.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangwei
 * @Date 2022/1/25 14:42
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Order(-2147483648)
public @interface SensitiveMethod {

    /**
     * 脱敏自定义字段
     */
    SensitiveParam[] value() default {};

}
