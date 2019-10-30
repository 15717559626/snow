package com.sun.snow.module.system.entity;

import lombok.Data;

import java.util.List;

@Data
public class LoginSuccessVO {
    /**
     * 用户编号
     */
    private String userId;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 角色信息
     */
    private List<String> roles;

    /**
     * 用户名
     */
    private String name;
}
