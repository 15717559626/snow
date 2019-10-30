package com.sun.snow.module.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.snow.module.system.entity.RolePermission;
import com.sun.snow.module.system.mapper.RolePermissionMapper;
import com.sun.snow.module.system.service.IRolePermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

}
