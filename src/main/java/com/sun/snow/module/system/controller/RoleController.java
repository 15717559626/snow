package com.sun.snow.module.system.controller;


import com.sun.snow.module.system.service.IRoleService;
import com.sun.snow.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author lish
 * @since 2019/10/29
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/list")
    public ResultUtil list() {
        return roleService.list();
    }
}

