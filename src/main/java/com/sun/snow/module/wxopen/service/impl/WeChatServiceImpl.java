package com.sun.snow.module.wxopen.service.impl;

import com.sun.snow.config.RobotContant;
import com.sun.snow.config.WeChatContant;
import com.sun.snow.http.HttpClientHandler;
import com.sun.snow.http.ResponseEntity;
import com.sun.snow.module.wxopen.model.ArticleItem;
import com.sun.snow.module.wxopen.service.WeChatService;
import com.sun.snow.util.DateUtil;
import com.sun.snow.util.WxMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: lish
 * @Description:
 * @Date: Create in 2019/7/12
 */
@Service
public class WeChatServiceImpl implements WeChatService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HttpClientHandler httpClientHandler;

    @Override
    public String processRequest(HttpServletRequest request) {
        try {
            // 默认返回的xml内容
            String respXml;
            // 默认返回的文本消息内容
            String respContent;
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = WxMessageUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get(WeChatContant.MsgType);
            String msg;
            msg = requestMap.get(WeChatContant.Content);
            switch (msgType){
                case WeChatContant.RESP_MESSAGE_TYPE_TEXT :
                    if (msg.contains("纪念日")  || msg.contains("在一起") || msg.contains("老公")){
                        respContent = "我们已经在一起" + DateUtil.getDays("2016-02-20","YYYY-MM-dd",0)+"天啦!";
                        respXml = WxMessageUtil.sendTextMsg(requestMap, respContent);
                    }else if ("我的信息".equals(msg)){
                        List<ArticleItem> items = new ArrayList<>();
                        ArticleItem item = new ArticleItem();
                        item.setTitle("我的信息");
                        item.setDescription("昵称:李松桦");
                        item.setPicUrl("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4254164563,3923686114&fm=26&gp=0.jpg");
                        item.setUrl("http://www.baidu.com");
                        items.add(item);
                        respXml = WxMessageUtil.sendArticleMsg(requestMap, items);
                    }else if (msg.contains("公交")){
                        List<ArticleItem> items = new ArrayList<>();
                        ArticleItem item = new ArticleItem();
                        item.setTitle("公交查询");
                        item.setDescription("公交查询链接");
                        item.setPicUrl("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=157190463,1216506805&fm=26&gp=0.jpg");
                        item.setUrl(RobotContant.BUSURL);
                        items.add(item);
                        respXml = WxMessageUtil.sendArticleMsg(requestMap, items);
                    }else {
                        String url = RobotContant.ROBOTURL + "?question=" + msg
                                +"&api_key=" + RobotContant.APIKEY
                                +"&api_secret=" + RobotContant.APISECRET;
                        //调用笨笨的机器人
                        ResponseEntity responseEntity = httpClientHandler.postJsonEntity(url,"");
                        respXml = WxMessageUtil.sendTextMsg(requestMap,responseEntity.getStrResult());
                    }
                    return respXml;
                case WeChatContant.REQ_MESSAGE_TYPE_IMAGE :
                    respContent = "暂不支持图片消息";
                    respXml = WxMessageUtil.sendTextMsg(requestMap, respContent);
                    return respXml;
                case WeChatContant.REQ_MESSAGE_TYPE_VOICE :
                    respContent = "暂不支持声音消息";
                    respXml = WxMessageUtil.sendTextMsg(requestMap, respContent);
                    return respXml;
                case WeChatContant.REQ_MESSAGE_TYPE_VIDEO :
                    respContent = "暂不支持视频消息";
                    respXml = WxMessageUtil.sendTextMsg(requestMap, respContent);
                    return respXml;
                default: respXml = WxMessageUtil.sendTextMsg(requestMap,"你好呀~");
            }
            logger.info("信息返回值为"+respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("processRequest-->",e);
        }
        return "";
    }
}
