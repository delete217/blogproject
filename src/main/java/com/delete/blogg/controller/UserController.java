package com.delete.blogg.controller;

import com.delete.blogg.service.ISysUserService;
import com.delete.blogg.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation("获取当前用户的信息")
    @GetMapping("/currentUser")
    //此处应该获取头部信息的token
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }
}
