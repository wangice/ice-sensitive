package com.ice.framework.sensitive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.ice.framework.config.SensitiveExpandItem;
import com.ice.framework.config.SensitiveProperties;
import com.ice.framework.enums.SensitiveType;
import com.ice.framework.sensitive.entity.SensitiveParamEntity;
import com.ice.framework.util.MiscUtil;
import com.ice.framework.util.SensitiveUtil;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ice.framework.util.SensitiveUtil.CONF;

/**
 * @author wangwei
 * @Date 2022/4/19 19:30
 */
@Slf4j
@Component
public class ConfigWayParse {

    @Autowired
    private SensitiveProperties sensitiveProperties;
    @Autowired
    private SensitiveParseField sensitiveParseField;

    /**
     * 脱敏字段
     * @Author wangwei
     * @Date 2022/4/19
     */
    public void updateSensitiveField(Object target) {
        List<String> scanPackages = SensitiveUtil.split(sensitiveProperties.getScanPackage());
        if (MiscUtil.isEmpty(scanPackages)) {
            return;
        }
        String packageName = target.getClass().getPackage().getName();
        if (!SensitiveUtil.containPath(scanPackages, packageName)) {
            return;
        }
        sensitiveField(target, sensitiveProperties.getFields().getUserName(), SensitiveType.CHINESE_NAME);
        sensitiveField(target, sensitiveProperties.getFields().getAddress(), SensitiveType.ADDRESS);
        sensitiveField(target, sensitiveProperties.getFields().getEmail(), SensitiveType.ADDRESS);
        sensitiveField(target, sensitiveProperties.getFields().getMobilePhone(), SensitiveType.MOBILE_PHONE);
        sensitiveField(target, sensitiveProperties.getFields().getIdentityCard(), SensitiveType.ID_CARD);
        if (MiscUtil.isNotEmpty(sensitiveProperties.getExpands())) {
            for (SensitiveExpandItem item:sensitiveProperties.getExpands()){
                sensitiveField(target, item);
            }
        }
    }

    /**
     * 脱敏用户名称
     * @Author wangwei
     * @Date 2022/4/19
     */
    private void sensitiveField(Object target, String field, SensitiveType sensitiveType) {
        // 封装成
        String[] names = StringUtils.split(field, MiscUtil.COMMA);
        if (MiscUtil.isEmpty(names)) {
            return;
        }
        SensitiveParamEntity entity = new SensitiveParamEntity();
        entity.setSensitiveType(sensitiveType);
        entity.setFields(names);
        List<String> values = Arrays.stream(names).map(m -> String.format("$..%s", m)).collect(Collectors.toList());
        updateJsonFieldValue(values, target, entity);
    }

    /**
     * 脱敏用户名称
     * @Author wangwei
     * @Date 2022/4/19
     */
    private void sensitiveField(Object target, SensitiveExpandItem item) {
        // 封装成
        String[] names = StringUtils.split(item.getField(), MiscUtil.COMMA);
        if (MiscUtil.isEmpty(names)) {
            return;
        }
        SensitiveParamEntity entity = new SensitiveParamEntity();
        entity.setFields(names);
        entity.setRegExp(item.getRegExp());
        entity.setRegStr(item.getRegStr());
        List<String> values = Arrays.stream(names).map(m -> String.format("$..%s", m)).collect(Collectors.toList());
        updateJsonFieldValue(values, target, entity);
    }

    /**
     * 更新JSON对象中的值
     * @Author wangwei
     * @Date 2022/4/19
     */
    public void updateJsonFieldValue(List<String> fields, Object target, SensitiveParamEntity item) {
        if (MiscUtil.isEmpty(fields)) {
            return;
        }
        for (String key : fields) {
            List<String> path;
            try {
                path = JsonPath.using(CONF).parse(JSON.toJSONString(target)).read(key);
            } catch (Exception e) {
                log.warn("PathNotFoundExceptionError,{}", key);
                continue;
            }
            if (path == null || path.isEmpty()) {
                continue;
            }
            path.forEach(p -> {
                if (JSONPath.contains(target, p)) {
                    Object value = JSONPath.eval(target, p);
                    //如果是NULL匹配,并且是封装类型,设置为空
                    if (item.getSensitiveType() == SensitiveType.NULL) {
                        JSONPath.set(target, p, null);
                    } else if (value instanceof String) {
                        JSONPath.set(target, p, sensitiveParseField.handlerSensitiveFeild(item, (String) value));
                    }
                }
            });
        }
    }

}
