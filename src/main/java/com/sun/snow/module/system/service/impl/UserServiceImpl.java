package com.sun.snow.module.system.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.snow.module.system.entity.User;
import com.sun.snow.module.system.mapper.UserMapper;
import com.sun.snow.module.system.entity.PermissionMap;
import com.sun.snow.module.system.service.IUserService;
import com.sun.snow.util.ResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public ResultUtil list() {
        List<User> list = list(new QueryWrapper<User>().lambda()
                .eq(User::getUserStatus, "0"));
        return ResultUtil.success(list);
    }

    @Override
    public ResultUtil logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            PermissionMap.map = null;
            PermissionMap.list = null;
        }
        return ResultUtil.success();
    }
}
