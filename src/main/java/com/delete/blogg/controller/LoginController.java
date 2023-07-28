package com.delete.blogg.controller;

import com.delete.blogg.service.LoginService;
import com.delete.blogg.vo.Result;
import com.delete.blogg.vo.params.LoginParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("登录接口")
    @PostMapping
    public Result login(@RequestBody LoginParams loginParams){

        return loginService.login(loginParams);

    }
}
