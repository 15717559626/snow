package com.sun.snow.module.system.entity;

import lombok.Data;
import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author lish
 * @description 权限相关
 * @date 2019/10/29 11:43
 */
@Data
public class PermissionMap {

    public static HashMap<String, Collection<ConfigAttribute>> map;

    public static List<PermissionDto> list;
}
