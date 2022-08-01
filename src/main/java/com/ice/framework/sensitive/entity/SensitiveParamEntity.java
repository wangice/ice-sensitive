package com.ice.framework.sensitive.entity;

import com.ice.framework.enums.SensitiveType;
import lombok.Data;

/**
 * @author wangwei
 * @Date 2022/4/20 12:00
 */
@Data
public class SensitiveParamEntity {

    // 脱敏类型
    private SensitiveType sensitiveType;
    // 脱敏字段
    private String[] fields;

    /**
     * 自定义正则匹配规则
     */
    private String regExp;

    /**
     * 正则替换字符
     */
    private String regStr;
}
