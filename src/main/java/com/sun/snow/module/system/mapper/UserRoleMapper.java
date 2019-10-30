package com.sun.snow.module.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.snow.module.system.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    List<String> roles(String userId);
}
