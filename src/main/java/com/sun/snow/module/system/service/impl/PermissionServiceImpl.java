package com.sun.snow.module.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.snow.module.system.entity.Permission;
import com.sun.snow.module.system.mapper.PermissionMapper;
import com.sun.snow.module.system.service.IPermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
