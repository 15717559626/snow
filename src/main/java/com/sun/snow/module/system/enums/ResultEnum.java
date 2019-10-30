package com.sun.snow.module.system.enums;

import lombok.Getter;

/**
 * @author: lish
 * @date: 2019/10/29 15:38
 * @description:错误枚举类
 */
@Getter
public enum ResultEnum {
    ACCESS_NOT(501, "权限不足"),

    TOKEN_IS_NOT_VALID(502, "token无效，请重新登录"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
