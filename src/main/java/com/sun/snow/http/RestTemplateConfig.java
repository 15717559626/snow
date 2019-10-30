package com.sun.snow.http;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : A127 ✌️
 * @date : 2018/5/14 19:10
 */
@Configuration
public class RestTemplateConfig {

    public RestTemplate restTemplate() {
        return restTemplate(10000, 10000);
    }

    /**
     * 为所有的消息转换器设定编码,彻底杜绝中文乱码问题
     */
    public RestTemplate restTemplate(int readTimeout, int connectTimeout) {
        RestTemplate restTemplate = new RestTemplate();
        Charset charset = Charset.forName("UTF-8");
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        ByteArrayHttpMessageConverter converter1 = new ByteArrayHttpMessageConverter();
        converter1.setDefaultCharset(charset);
        StringHttpMessageConverter converter2 = new StringHttpMessageConverter(charset);
        ResourceHttpMessageConverter converter3 = new ResourceHttpMessageConverter();
        converter3.setDefaultCharset(charset);
        SourceHttpMessageConverter<Source> converter4 = new SourceHttpMessageConverter<Source>();
        converter4.setDefaultCharset(charset);
        AllEncompassingFormHttpMessageConverter converter5 = new AllEncompassingFormHttpMessageConverter();
        converter5.setCharset(charset);
        converter5.setMultipartCharset(charset);
        Jaxb2RootElementHttpMessageConverter converter6 = new Jaxb2RootElementHttpMessageConverter();
        converter6.setDefaultCharset(charset);
        FastJsonHttpMessageConverter converter7 = new FastJsonHttpMessageConverter();
        converter7.setCharset(charset);
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(7);
        converters.add(converter1);
        converters.add(converter2);
        converters.add(converter3);
        converters.add(converter4);
        converters.add(converter5);
        converters.add(converter6);
        converters.add(converter7);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }
}
