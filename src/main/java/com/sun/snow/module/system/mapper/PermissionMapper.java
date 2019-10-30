package com.sun.snow.module.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.snow.module.system.entity.Permission;
import com.sun.snow.module.system.entity.PermissionDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 返回所有角色资源
     *
     * @return
     */
    List<PermissionDto> findAll();
}
