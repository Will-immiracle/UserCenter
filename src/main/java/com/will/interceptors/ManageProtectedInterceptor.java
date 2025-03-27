package com.will.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.pojo.User;
import com.will.utils.JwtHelper;
import com.will.utils.Result;
import com.will.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @program: UserCenter
 * @description: 拦截器
 * @author: Mr.Zhang
 * @create: 2025-03-27 16:10
 **/

@Component
public class ManageProtectedInterceptor implements HandlerInterceptor {
    @Autowired
    JwtHelper jwtHelper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        boolean expiration = jwtHelper.isExpiration(token);
        if(!expiration){
            return true;
        }
        Result result = new Result<User>().build(null, ResultCodeEnum.NOTLOGIN);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().write(json);
        return false;
    }
}
