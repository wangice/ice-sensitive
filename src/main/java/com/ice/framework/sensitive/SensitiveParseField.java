package com.ice.framework.sensitive;

import com.ice.framework.annotation.SensitiveParam;
import com.ice.framework.convert.*;
import com.ice.framework.enums.SensitiveType;
import com.ice.framework.sensitive.entity.SensitiveParamEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangwei
 * @Date 2022/4/19 20:00
 */
@Slf4j
@Component
public class SensitiveParseField {

    @Autowired
    private IdentityCardConvert identityCardConvert;
    @Autowired
    private NameConvert nameConvert;
    @Autowired
    private PhoneConvert phoneConvert;
    @Autowired
    private EmailConvert emailConvert;
    @Autowired
    private AddressConvert addressConvert;

    /**
     * 字段脱敏
     * @param sensitiveParam
     * @param value
     * @return
     */
    public String handlerSensitiveFeild(SensitiveParam sensitiveParam, String value) {
        if (sensitiveParam == null) {
            return value;
        }
        //有正则优先使用正则
        if (StringUtils.isNotBlank(sensitiveParam.regExp())) {
            return value.replaceAll(sensitiveParam.regExp(), sensitiveParam.regStr());
        }
        //是NULL类型,直接返回null
        if (sensitiveParam.type() == SensitiveType.NULL) {
            return null;
        }
        try {
            switch (sensitiveParam.type()) {
                case CHINESE_NAME:
                    return nameConvert.convertSensitive(value);
                case ID_CARD:
                    return identityCardConvert.convertSensitive(value);
                case MOBILE_PHONE:
                    return phoneConvert.convertSensitive(value);
                case ADDRESS:
                    return addressConvert.convertSensitive(value);
                case EMAIL:
                    return emailConvert.convertSensitive(value);
                default:
                    return value;
            }
        } catch (Exception e) {
            log.error("脱敏数据处理异常", e);
        }
        return value;
    }

    public String handlerSensitiveFeild(SensitiveParamEntity sensitiveParam, String value) {
        if (sensitiveParam == null) {
            return value;
        }
        //有正则优先使用正则
        if (StringUtils.isNotBlank(sensitiveParam.getRegExp())) {
            return value.replaceAll(sensitiveParam.getRegExp(), sensitiveParam.getRegStr());
        }
        //是NULL类型,直接返回null
        if (sensitiveParam.getSensitiveType() == SensitiveType.NULL) {
            return null;
        }
        try {
            switch (sensitiveParam.getSensitiveType()) {
                case CHINESE_NAME:
                    return nameConvert.convertSensitive(value);
                case ID_CARD:
                    return identityCardConvert.convertSensitive(value);
                case MOBILE_PHONE:
                    return phoneConvert.convertSensitive(value);
                case ADDRESS:
                    return addressConvert.convertSensitive(value);
                case EMAIL:
                    return emailConvert.convertSensitive(value);
                default:
                    return value;
            }
        } catch (Exception e) {
            log.error("脱敏数据处理异常", e);
        }
        return value;
    }
}
