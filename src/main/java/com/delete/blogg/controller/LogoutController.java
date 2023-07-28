package com.delete.blogg.controller;

import com.delete.blogg.service.LoginService;
import com.delete.blogg.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("登录接口")
    @GetMapping
    public Result logout(String token){
        return loginService.logout(token);
    }
}
