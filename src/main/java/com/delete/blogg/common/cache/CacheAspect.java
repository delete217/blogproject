package com.delete.blogg.common.cache;

import com.alibaba.fastjson.JSON;
import com.delete.blogg.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;

@Component
@Aspect
@Slf4j
public class CacheAspect {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(com.delete.blogg.common.cache.Cache)")
    public void pt() {
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Signature signature = proceedingJoinPoint.getSignature();
            String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
            String methodName = signature.getName();

            Class[] parameterTypes = new Class[proceedingJoinPoint.getArgs().length];
            Object[] args = proceedingJoinPoint.getArgs();

            String params = "";
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                } else {
                    parameterTypes[i] = null;
                }
            }
            if (!StringUtils.isEmpty(params)) {
                params = DigestUtils.md5Hex(params);
            }
            Method method = proceedingJoinPoint.getSignature().getDeclaringType().getDeclaredMethod(methodName, parameterTypes);

            Cache annotation = method.getAnnotation(Cache.class);
            long expire = annotation.expire();
            String name = annotation.name();

            String redisKey = name + "::" + className + "::" + methodName + "::" + params;
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (!StringUtils.isEmpty(redisValue)) {
                log.info("走了缓存~~~,{},{}", className, methodName);
                return JSON.parseObject(redisValue, Result.class);
            }
            Object proceed = proceedingJoinPoint.proceed();
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存~~~{},{}", className, methodName);
            return proceed;
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
        return Result.fail(-999,"系统错误");


    }

}
