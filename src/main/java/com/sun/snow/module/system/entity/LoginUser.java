package com.sun.snow.module.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: lish
 * @date: 2019/10/29 16:03
 * @description:
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -3025888694030595940L;
    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 是否记住密码
     */
    private Boolean remember;

}
