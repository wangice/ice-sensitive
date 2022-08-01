package com.ice.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangwei
 * @Date 2022/4/20 14:05
 */
@Data
@ConfigurationProperties(prefix = "sensitive.fields")
@Configuration
public class SensitiveFieldProperties {

    // 电话号码字段
    private String mobilePhone;

    // 身份证
    private String identityCard;

    // 用户名
    private String userName;

    // 地址
    private String address;

    //邮件
    private String email;
}
