package com.delete.blogg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.delete.blogg.entity.SysUser;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.params.LoginParams;
import org.springframework.stereotype.Service;


public interface LoginService{
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParams loginParams);

}
