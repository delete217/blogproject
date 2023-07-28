package com.delete.blogg.handler;

import com.alibaba.fastjson.JSON;
import com.delete.blogg.entity.SysUser;
import com.delete.blogg.service.LoginService;
import com.delete.blogg.utils.UserThreadLocal;
import com.delete.blogg.vo.EnumError;
import com.delete.blogg.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 在执行controller方法前执行
         * 1. 需要判断 请求的接口路径 是否为HandlerMethod（Controller方法）
         * 2. 判断token是否为空，如果为空 则未登录
         * 3. 如果token不为空 登录验证 loginService.checkToken
         * 4. 如果认证成功 放行
         */
        if (!(handler instanceof HandlerMethod)){
            //有可能是访问静态资源 直接放行
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("=========request start==========");
        log.info("request uri:{}",request.getRequestURI());
        log.info("request method:{}",request.getMethod());
        log.info("token:{}",token);
        log.info("=========request end==========");

        if (StringUtils.isEmpty(token)){
            Result result = Result.fail(EnumError.NOT_LOGIN.getCode(), EnumError.NOT_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(EnumError.NOT_LOGIN.getCode(), EnumError.NOT_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        //登陆成功 放行
        //在controller中直接获取用户的信息
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //删除ThreadLocal中的信息
        UserThreadLocal.remove();
    }
}
