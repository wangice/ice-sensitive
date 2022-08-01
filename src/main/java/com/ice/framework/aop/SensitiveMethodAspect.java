package com.ice.framework.aop;

import cn.hutool.core.util.ObjectUtil;
import com.ice.framework.annotation.SensitiveMethod;
import com.ice.framework.annotation.SensitiveParam;
import com.ice.framework.sensitive.AnnotationWayParse;
import com.ice.framework.sensitive.ConfigWayParse;
import com.ice.framework.util.MiscUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * SensitiveMethod 切面
 * @author wangwei
 * @Date 2022/1/25 14:44
 */
@Slf4j
@Aspect
@Component
public class SensitiveMethodAspect extends SensitiveAbstract {


    @Autowired
    private ConfigWayParse configWayParse;

    @Autowired
    private AnnotationWayParse annotationWayParse;
    /**
     * 拦截器
     * @Author wangwei
     * @Date 2022/4/19
     */
    @Around("@annotation(com.ice.framework.annotation.SensitiveMethod)")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        if (ObjectUtil.isEmpty(proceed)) {
            return proceed;
        }
        Object target = proceed;
        if (joinPoint.getThis().getClass().getSuperclass().isAnnotationPresent(Controller.class) || joinPoint.getThis().getClass().getSuperclass().isAnnotationPresent(RestController.class)) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            SensitiveMethod annotation = methodSignature.getMethod().getAnnotation(SensitiveMethod.class);
            if (annotation == null) {
                return target;
            }
            List<SensitiveParam> sensitiveParams = getDes(annotation);
            return filterValue(sensitiveParams, target);
        }
        return target;
    }

    /**
     * 获取所有注解
     * @param annotation
     */
    private List<SensitiveParam> getDes(SensitiveMethod annotation) {
        if (annotation == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(annotation.value()).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 处理
     * @Author wangwei
     * @Date 2022/2/24
     */
    private Object filterValue(List<SensitiveParam> sensitiveParams, Object target) {
        if (MiscUtil.isEmpty(sensitiveParams)) {
            configWayParse.updateSensitiveField(target);
            return target;
        }
        annotationWayParse.updateSensitiveField(target,sensitiveParams);
        return target;
    }
}
