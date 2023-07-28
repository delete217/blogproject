package com.delete.blogg.controller;


import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author delete
 * @since 2022-10-27
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/hello")
    public String test(){
        return "hello";
    }

}
