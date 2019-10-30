package com.sun.snow.module.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.snow.module.system.entity.Role;
import com.sun.snow.module.system.mapper.RoleMapper;
import com.sun.snow.module.system.service.IRoleService;
import com.sun.snow.util.ResultUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public ResultUtil list() {
        List<Role> list = list(new QueryWrapper<Role>().lambda()
                .eq(Role::getRoleStatus, '0'));
        return ResultUtil.success(list);
    }
}
