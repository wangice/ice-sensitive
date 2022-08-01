package com.ice.framework.convert;

/**
 * 身份证脱敏
 * @author wangwei
 * @Date 2022/1/25 14:29
 */
public interface IdentityCardConvert {


    /**
     * 身份证脱敏方法
     */
    String convertSensitive(String word);
}
