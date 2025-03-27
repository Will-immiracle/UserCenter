package com.will.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    @TableId
    private Long id;

    private String username;

    private String userAccount;

    private String avatarUrl;

    private Integer gender;

    private String userPassword;

    private String phone;

    private String email;

    private Integer userStatus;

    private Date createTime;

    private Date updateTime;
    @TableLogic
    private Integer isDeleted;
}