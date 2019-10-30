package com.sun.snow.module.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.snow.module.system.entity.UserRole;
import com.sun.snow.module.system.mapper.UserRoleMapper;
import com.sun.snow.module.system.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
