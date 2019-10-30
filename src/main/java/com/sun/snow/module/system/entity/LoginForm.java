package com.sun.snow.module.system.entity;

import lombok.Data;

/**
 * @author: lish
 * @date: 2019/10/29 15:41
 * @description:登录请求参数
 */
@Data
public class LoginForm extends LoginUser {

    /**
     * 用户密码
     */
    private String userPassword;

    private String userPhone;

    private boolean remeber;

}
