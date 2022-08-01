package com.ice.framework.util;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangwei
 * @Date 2022/1/25 10:27
 */
public class SensitiveUtil {

    public final static char DEFAULT_MASK = '*';

    public final static Configuration CONF = Configuration.builder().options(Option.AS_PATH_LIST, Option.DEFAULT_PATH_LEAF_TO_NULL).build();

    /**
     * 脱敏规则
     * @Param origin 原始字符串
     * @param startInclude 左侧需要保留几位明文字段
     * @param endExclude 右侧需要保留几位明文字段
     * @param replacedChar 用于遮罩的字符串, 如'*'
     * @Return 脱敏后结果
     */
    public static String replace(CharSequence str, int startInclude, int endExclude, char replacedChar) {
        if (StringUtils.isEmpty(str)) {
            return str(str);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0, n = str.length(); i < n; i++) {
                if (i < startInclude) {
                    sb.append(str.charAt(i));
                    continue;
                }
                if (i > (n - endExclude - 1)) {
                    sb.append(str.charAt(i));
                    continue;
                }
                sb.append(replacedChar);
            }
            return sb.toString();
        }
    }

    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    /**
     * 获取扫描表集合
     * @Author wangwei
     * @Date 2022/4/19
     */
    public static List<String> split(String str) {
        String[] split = StringUtils.split(str, MiscUtil.COMMA);
        return Arrays.asList(split);
    }

    /**
     * 判断路径是在集合中
     * @Author wangwei
     * @Date 2022/4/19
     */
    public static boolean containPath(List<String> paths, String descPath) {
        for (String path : paths) {
            if (path.contains(descPath)) {
                return true;
            }
        }
        return false;
    }
}
