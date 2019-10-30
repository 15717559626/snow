package com.sun.snow.module.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.sun.snow.module.system.entity.LoginUser;
import com.sun.snow.module.system.mapper.UserMapper;
import com.sun.snow.module.system.entity.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public UserDetails loadUserByUsername(String json) throws UsernameNotFoundException {
        LoginUser loginUser = JSON.parseObject(json, LoginUser.class);
        UserDTO userDTO = userMapper.getUserDTO(loginUser.getUserPhone());
        if (userDTO == null) {
            throw new UsernameNotFoundException(String.format("No user found with userPhone '%s'.", loginUser.getUserPhone()));
        }
        userDTO.setRemember(true);
        userDTO.setName(userDTO.getUsername());
        userDTO.setUserName(json);
        //返回用户的权限
        return userDTO;
    }
}
