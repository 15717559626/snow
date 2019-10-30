package com.sun.snow.module.system.controller;



import com.sun.snow.module.system.service.IUserService;
import com.sun.snow.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public ResultUtil list() {
        return userService.list();
    }

    @GetMapping("/hello")
    public ResultUtil hello() {
        return ResultUtil.success("不需要权限也可以访问我啊！");
    }

    @GetMapping("/logout")
    public ResultUtil logout(HttpServletRequest request, HttpServletResponse response) {
        return userService.logout(request, response);
    }
}

