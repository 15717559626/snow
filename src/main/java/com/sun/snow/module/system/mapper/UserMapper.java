package com.sun.snow.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.snow.module.system.entity.User;
import com.sun.snow.module.system.entity.UserDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 登录校验
     *
     * @param phone
     * @return
     */
    UserDTO getUserDTO(String phone);
}
