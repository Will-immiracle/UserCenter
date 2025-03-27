package com.will;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: UserCenter
 * @description: 入口类
 * @author: Mr.Zhang
 * @create: 2025-03-26 20:31
 **/

@MapperScan("com.will.mapper")
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(MainApplication.class, args);
    }
}
