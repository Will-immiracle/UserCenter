package com.will.controller;

import com.will.pojo.User;
import com.will.service.UserService;
import com.will.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: UserCenter
 * @description: 用户功能
 * @author: Mr.Zhang
 * @create: 2025-03-26 21:12
 **/

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    public Result register(@RequestBody User user){
        Result result = userService.userRegister(user);
        return result;
    }
    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.userLogin(user);
        return result;
    }

}
