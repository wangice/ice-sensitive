package com.ice.framework.annotation;

import com.ice.framework.enums.HandleType;
import com.ice.framework.enums.SensitiveType;

import java.lang.annotation.*;

/**
 * @author wangwei
 * @Date 2022/2/24 15:58
 */
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveParam {

    /*脱敏类型,默认电话号码*/
    SensitiveType type() default SensitiveType.MOBILE_PHONE;

    /*脱敏字段,默认phone*/
    String[] fields() default "phone";

    /*处理方式,默认字段相等匹配*/
    HandleType mode() default HandleType.DEFAULT;

    /**
     * 自定义正则匹配规则
     */
    String regExp() default "";

    /**
     * 正则替换字符
     */
    String regStr() default "*";
}
