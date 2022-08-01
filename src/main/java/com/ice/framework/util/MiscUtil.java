package com.ice.framework.util;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

/**
 * @author wangwei
 * @Date 2022/3/30 17:11
 */
@Slf4j
public class MiscUtil {

    public static final String COMMA = ",";

    public static final String SEMICOLON = ";";

    /**
     * 将字符串生成唯一longHash编码
     * @Author wangwei
     * @Date 2022/3/30
     */
    public static Long longHash(String str) {
        long h = 98764321261L;
        int l = str.length();
        char[] chars = str.toCharArray();

        for (int i = 0; i < l; i++) {
            h = 31 * h + chars[i];
        }
        return h;
    }

    /**
     * 休眠不抛异常
     * @param time
     */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.warn("休眠异常：{}", ExceptionUtil.stacktraceToString(e));
        }
    }

    public static boolean isBaseType(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return true;
        } else {
            return clazz.getName().startsWith("java.lang.");
        }
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return "".equals(object.toString());
        } else if (object instanceof Collection) {
            return ((Collection)object).isEmpty();
        } else if (object instanceof Object[]) {
            return ((Object[])((Object[])object)).length == 0;
        } else if (object instanceof Map) {
            return ((Map)object).isEmpty();
        } else{
            return false;
        }
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }
}
