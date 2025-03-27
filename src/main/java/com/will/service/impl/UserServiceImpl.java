package com.will.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.will.mapper.UserMapper;
import com.will.pojo.User;
import com.will.service.UserService;
import com.will.utils.MD5Util;
import com.will.utils.Result;
import com.will.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author zhangzan
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-03-26 21:05:59
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    UserMapper userMapper;

    /*
    * 用户注册
    *
    * 1.检验账号是否符合规范（只允许字母、数字、下划线）
    * 2.密码（>7）和账户(>3)长度、空值（not null）检查
    * 3.检查账户是否重复
    * 4.对用户密码进行加密
    * 5.插入数据
    * */
    @Override
    public Result userRegister(User user) {
        //1.检验账号是否符合规范（只允许字母、数字、下划线）
        String validPattern = "^[a-zA-Z0-9_]+$";
        Pattern pattern=Pattern.compile(validPattern);
        Matcher matcher = pattern.matcher(user.getUserAccount());
        if (!matcher.matches()){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        //2.密码（>7）和账户(>3)长度、空值（not null）检查
        if (StringUtils.isEmpty(user.getUserAccount()) || StringUtils.isEmpty(user.getUserPassword()) || StringUtils.isEmpty(user.getUsername())
                || user.getUserPassword().length()<7 ||user.getUserAccount().length()<4){
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
        //3.检查账户是否重复
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount,user.getUserAccount());
        Long count = userMapper.selectCount(queryWrapper);
        if (count>0){
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        //4.对用户密码进行加密
        String encrypt = MD5Util.encrypt(user.getUserPassword());
        user.setUserPassword(encrypt);
        //5.插入数据
        int insert = userMapper.insert(user);
        if (insert==0){
            return Result.build(null, ResultCodeEnum.DATABASE_ERROR);
        }
        return Result.ok(null);
    }
}




