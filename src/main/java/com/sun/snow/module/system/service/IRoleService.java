package com.sun.snow.module.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.snow.module.system.entity.Role;
import com.sun.snow.util.ResultUtil;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
public interface IRoleService extends IService<Role> {

    /**
     * 角色列表
     *
     * @return
     */
    ResultUtil list();
}
