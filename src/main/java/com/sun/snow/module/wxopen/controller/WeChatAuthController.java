package com.sun.snow.module.wxopen.controller;

import com.sun.snow.module.wxopen.service.WeChatService;
import com.sun.snow.util.IpUtil;
import com.sun.snow.util.WxMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lish
 * @Description:
 * @Date: Create in 2019/7/12
 */
@RestController
@RequestMapping("/wxMessage")
public class WeChatAuthController {

    @Autowired
    private WeChatService weChatService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/receiveMessage")
    public String validate(@RequestParam(value = "signature") String signature,
                           @RequestParam(value = "timestamp") String timestamp,
                           @RequestParam(value = "nonce") String nonce,
                           @RequestParam(value = "echostr") String echostr) {
        return WxMessageUtil.checkSignnature(signature, timestamp, nonce) ? echostr : null;
    }

    @PostMapping("/receiveMessage")
    public String processMsg(HttpServletRequest request){
        String ip = IpUtil.getRemoteIP(request);
        logger.info("请求的ip为"+ip);
        return weChatService.processRequest(request);
    }


}
