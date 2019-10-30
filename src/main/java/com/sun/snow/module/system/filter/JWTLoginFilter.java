package com.sun.snow.module.system.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.snow.module.system.entity.LoginSuccessVO;
import com.sun.snow.module.system.entity.LoginForm;
import com.sun.snow.module.system.entity.LoginUser;
import com.sun.snow.module.system.mapper.UserMapper;
import com.sun.snow.module.system.entity.User;
import com.sun.snow.module.system.entity.UserDTO;
import com.sun.snow.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author: lish
 * @date: 2019/10/29 15:28
 * @description:token jwt登陆过滤器
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 请求登录
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginForm loginForm = new ObjectMapper().readValue(request.getInputStream(), LoginForm.class);
            checkLoginForm(loginForm, response);
            LoginUser loginUser = new LoginUser();
            BeanUtils.copyProperties(loginForm, loginUser);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(JSON.toJSONString(loginUser), loginForm.getUserPassword(), new ArrayList<>()));
        } catch (IOException e) {
            ResponseUtil.write(response, ResultUtil.error("数据读取错误"));
        }
        return null;
    }
    /**
     * 登录成功后
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDTO userDTO = (UserDTO) authResult.getPrincipal();
        if (jwtTokenUtil == null) {
            jwtTokenUtil = (JwtTokenUtil) SpringUtils.getBean("jwtTokenUtil");
        }
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUserLastLoginTime(TimeUtil.nowTimeStamp());
        if (userMapper == null) {
            userMapper = (UserMapper) SpringUtils.getBean("userMapper");
        }
        //更新登最近一次录时间
        userMapper.updateById(user);
        String token = jwtTokenUtil.createToken(userDTO);
        //将token放置请求头返回
        response.addHeader(jwtTokenUtil.getTokenHeader(), jwtTokenUtil.getTokenPrefix() + token);
        LoginSuccessVO loginSuccessVO = new LoginSuccessVO();
        BeanUtils.copyProperties(userDTO, loginSuccessVO);
        ResponseUtil.write(response, ResultUtil.success(loginSuccessVO));
    }

    /**
     * 登录失败
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.write(response, ResultUtil.error(failed.getMessage()));
    }

    /**
     * 校验参数
     *
     * @param loginForm
     */
    private void checkLoginForm(LoginForm loginForm, HttpServletResponse response) {
        if (StringUtils.isEmpty(loginForm.getUserPhone())) {
            ResponseUtil.write(response, ResultUtil.error("手机号不能为空"));
            return;
        }
        if (StringUtils.isEmpty(loginForm.getUserPassword())) {
            ResponseUtil.write(response, ResultUtil.error("密码不能为空"));
            return;
        }
    }

}
