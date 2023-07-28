package com.delete.blogg.common.aop;

import com.alibaba.fastjson.JSON;
import com.delete.blogg.utils.HttpContextUtils;
import com.delete.blogg.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.delete.blogg.common.aop.LogAnnotation)")
    public void pt() {
    }

    //环绕通知 对方法前后都进行增强\
    //记录日志
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长
        long time = System.currentTimeMillis() - beginTime;
        recordLog(joinPoint, time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("===============logStart===============");
        log.info("module:{}", logAnnotation.module());
        log.info("operation:{}", logAnnotation.operator());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}",params);

        //获取request 设置ip地址
        HttpServletRequest httpServletRequest = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(httpServletRequest));

        log.info("excute time:{} ms",time);
        log.info("===========================");
    }


}
