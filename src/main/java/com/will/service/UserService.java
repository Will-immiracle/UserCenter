package com.will.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.will.pojo.User;
import com.will.utils.Result;

/**
* @author zhangzan
* @description 针对表【user】的数据库操作Service
* @createDate 2025-03-26 21:05:59
*/
public interface UserService extends IService<User> {

    Result userRegister(User user);

    Result userLogin(User user);

    Result deleteUser(String userAccount);

    Result findUserById(String userAccount);

    Result updateUser(User user);
}
