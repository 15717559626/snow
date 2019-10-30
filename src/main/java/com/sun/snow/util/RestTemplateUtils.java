package com.sun.snow.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;

/**
 * Spring RestTemplate工具类
 *
 * @author : yhy
 * @date : 2018/4/27 20:27
 */
@Slf4j
@Component
public class RestTemplateUtils {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送post请求,返回String类型数据
     *
     * @param url            请求URL地址
     * @param data           json数据
     * @param contentType    请求数据格式
     * @param readTimeout    读取时间
     * @param connectTimeout 连接超时时间
     */

    public String post(String url, String data, MediaType contentType, MediaType accept, int readTimeout, int connectTimeout) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.setAccept(Collections.singletonList(accept));
        HttpEntity<String> entity = new HttpEntity<>(data, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    }

    /**
     * 发送post请求,返回String类型数据
     *
     * @param url       请求URL地址
     * @param data      json数据
     * @param mediaType 请求数据格式
     */
    public String post(String url, String data, MediaType mediaType) {
        return post(url, data, mediaType, MediaType.APPLICATION_JSON, 0, 0);
    }

    /**
     * 发送post请求
     *
     * @param url          请求链接
     * @param request      请求内容
     * @param responseType 响应类型
     * @return T
     */
    public <T> T postForObject(String url, Object request, Class<T> responseType) {
        return postForObject(url, request, responseType, 0, 0);

    }

    /**
     * 发送post请求
     *
     * @param url            请求链接
     * @param request        请求内容
     * @param responseType   响应类型
     * @param readTimeout    读取时间
     * @param connectTimeout 连接超时时间
     * @return T
     */
    public <T> T postForObject(String url, Object request, Class<T> responseType, int readTimeout, int connectTimeout) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Encoding", "UTF-8");
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        String requestXml = JSONObject.toJSONString(request);
        HttpEntity<String> entity = new HttpEntity<>(requestXml, headers);
        return restTemplate.postForObject(url, entity, responseType);
    }


    public <T> T getForObject(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);

    }

}
