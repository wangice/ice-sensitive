package com.ice.framework.config;

import com.ice.framework.convert.*;
import com.ice.framework.util.SensitiveUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangwei
 * @Date 2022/1/25 14:39
 */
@Configuration
public class SensitiveGlobalConfig {


    /**
     * 默认省份证过敏
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(IdentityCardConvert.class)
    public IdentityCardConvert identityCardConvert() {
        return word -> SensitiveUtil.replace(word, 6, 3, SensitiveUtil.DEFAULT_MASK);
    }

    /**
     * 默认名称脱敏
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(NameConvert.class)
    public NameConvert nameConvert() {
        return word -> {
            {
                if (StringUtils.isBlank(word)) {
                    return "";
                }
                if (word.length() == 2) {
                    return SensitiveUtil.replace(word, 1, 0, SensitiveUtil.DEFAULT_MASK);
                }
                if (word.length() > 2) {
                    return SensitiveUtil.replace(word, 1, 1, SensitiveUtil.DEFAULT_MASK);
                }
                return word;
            }
        };

    }

    /**
     * 默认电话号码脱敏
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PhoneConvert.class)
    public PhoneConvert phoneConvert() {
        return word -> SensitiveUtil.replace(word, 3, 3, SensitiveUtil.DEFAULT_MASK);
    }

    /**
     * 默认地址脱敏
     * @Author wangwei
     * @Date 2022/4/2
     */
    @Bean
    @ConditionalOnMissingBean(AddressConvert.class)
    public AddressConvert addressConvert(){
        return word -> {
            if(word.length() <= 8){
                return word;
            }
            int length = StringUtils.length(word);
            return StringUtils.rightPad(StringUtils.left(word, length - 8), length, "*");
        };
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     * @Author wangwei
     * @Date 2022/4/2
     */
    @Bean
    @ConditionalOnMissingBean(EmailConvert.class)
    public EmailConvert emailConvert(){
        return email -> {
            final int index = StringUtils.indexOf(email, "@");
            if (index <= 1) {
                return email;
            } else {
                return StringUtils.rightPad(StringUtils.left(email, 1), index, "*")
                        .concat(StringUtils.mid(email, index, StringUtils.length(email)));
            }
        };
    }
}
