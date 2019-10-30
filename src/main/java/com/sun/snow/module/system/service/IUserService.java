package com.sun.snow.module.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.snow.module.system.entity.User;
import com.sun.snow.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Lan
 * @since 2019/10/29
 */
public interface IUserService extends IService<User> {

    /**
     * 用户列表
     *
     * @return
     */
    ResultUtil list();

    /**
     * 注销
     *
     * @param request
     * @param response
     * @return
     */
    ResultUtil logout(HttpServletRequest request, HttpServletResponse response);
}
