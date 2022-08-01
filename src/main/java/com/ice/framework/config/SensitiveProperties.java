package com.ice.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wangwei
 * @Date 2022/4/19 14:24
 */
@Data
@ConfigurationProperties(prefix = "sensitive")
@Configuration
public class SensitiveProperties {

    /**
     * 配置脱敏字段
     */
    public SensitiveFieldProperties fields;

    /**
     * 扫描包路径(多个已逗号分隔)
     */
    public String scanPackage;

    /**
     * 用户扩展信息
     */
    public List<SensitiveExpandItem> expands;
}
