package com.will.controller;

import com.will.pojo.User;
import com.will.service.UserService;
import com.will.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    /*
    * 用户注册
    * */
    @PostMapping
    public Result register(@RequestBody User user){
        Result result = userService.userRegister(user);
        return result;
    }
    /*
    * 用户登录
    * */
    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.userLogin(user);
        return result;
    }
    /*
    * 用户注销
    * 注：鉴定权限！(token验证拦截器)
    * */
    @GetMapping("manage/delete")
    public Result deleteUser(@RequestParam Long id){
        Result result = userService.deleteUser(id);
        return result;
    }
    /*
    * 查询用户
    * 鉴定权限！
    * */
    @GetMapping("manage/search")
    public Result findUser(@RequestParam Long id){
        Result result = userService.findUserById(id);
        return result;
    }
    /*
    * 用户更新
    * 权限！
    * */

    @PostMapping("manage/update")
    public Result updateUser(@RequestBody User user){
        Result result = userService.updateUser(user);
        return result;
    }


}
