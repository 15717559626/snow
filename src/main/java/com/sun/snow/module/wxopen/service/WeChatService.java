package com.sun.snow.module.wxopen.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lish
 * @Description:
 * @Date: Create in 2019/7/12
 */
public interface WeChatService {

    /***
    * @Author: lish
    * @Date: 2019/7/12
    * @Description: 处理用户请求
    * @Param:  * @param request
    */
    public String processRequest(HttpServletRequest request);

}
