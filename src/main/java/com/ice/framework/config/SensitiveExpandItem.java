package com.ice.framework.config;

import lombok.Data;

/**
 * @author ice
 * @Date 2022/7/19 0019 00:08
 */
@Data
public class SensitiveExpandItem {

    /**
     * 脱敏字段，多个以逗号分隔
     */
    private String field;

    /**
     * 自定义正则匹配规则
     */
    private String regExp;

    /**
     * 正则替换字符
     */
    private String regStr = "*";

}
