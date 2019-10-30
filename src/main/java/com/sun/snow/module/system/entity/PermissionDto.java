package com.sun.snow.module.system.entity;

import lombok.Data;

import java.util.List;

/**
 * @author: lish
 * @date: 2019/10/29 10:42
 * @description:角色资源
 */
@Data
public class PermissionDto {

    /**
     * url
     */
    private String permissionUrl;

    /**
     * 角色名称
     */
    private List<String> roleNames;
}
