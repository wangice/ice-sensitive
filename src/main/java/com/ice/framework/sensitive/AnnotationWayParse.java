package com.ice.framework.sensitive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.ice.framework.annotation.SensitiveParam;
import com.ice.framework.enums.HandleType;
import com.ice.framework.enums.SensitiveType;
import com.ice.framework.util.MiscUtil;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ice.framework.util.SensitiveUtil.CONF;

/**
 * @author wangwei
 * @Date 2022/4/19 14:50
 */
@Slf4j
@Component
public class AnnotationWayParse {

    @Autowired
    private SensitiveParseField sensitiveParseField;

    /**
     * 脱敏字段
     * @Author wangwei
     * @Date 2022/4/19
     */
    public void updateSensitiveField(Object target, List<SensitiveParam> sensitiveParams) {
        for (SensitiveParam item : sensitiveParams) {
            String[] fields = item.fields();
            if (fields.length == 0) {
                continue;
            }
            List<String> values = new ArrayList<>();
            if (item.mode() == HandleType.DEFAULT) {
                values = Arrays.stream(fields).map(m -> String.format("$..%s", m)).collect(Collectors.toList());
            }
            updateJsonFieldValue(values, target, item);
        }
    }

    /**
     * 更新JSON对象中的值
     * @Author wangwei
     * @Date 2022/4/19
     */
    public void updateJsonFieldValue(List<String> fields, Object target, SensitiveParam item) {
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
                    if (item.type() == SensitiveType.NULL) {
                        JSONPath.set(target, p, null);
                    } else if (value instanceof String) {
                        JSONPath.set(target, p, sensitiveParseField.handlerSensitiveFeild(item, (String) value));
                    }
                }
            });
        }
    }
}
