package com.sun.snow;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sun.snow.config.HttpClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan("com.sun.snow.module.*.mapper")
@EnableWebSecurity
public class SnowApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(SnowApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(HttpClientConfig.build());
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(15000);
        RestTemplate template = new RestTemplate();
        Charset charset = Charset.forName("UTF-8");
        ByteArrayHttpMessageConverter converter1=new ByteArrayHttpMessageConverter();
        converter1.setDefaultCharset(charset);
        StringHttpMessageConverter converter2=new StringHttpMessageConverter(charset);
        ResourceHttpMessageConverter converter3=new ResourceHttpMessageConverter();
        converter3.setDefaultCharset(charset);
        SourceHttpMessageConverter<Source> converter4=new SourceHttpMessageConverter<Source>();
        converter4.setDefaultCharset(charset);
        AllEncompassingFormHttpMessageConverter converter5=new AllEncompassingFormHttpMessageConverter();
        converter5.setCharset(charset);
        converter5.setMultipartCharset(charset);
        Jaxb2RootElementHttpMessageConverter converter6=new Jaxb2RootElementHttpMessageConverter();
        converter6.setDefaultCharset(charset);
        FastJsonHttpMessageConverter converter7=new FastJsonHttpMessageConverter();
        converter7.setCharset(charset);
        List<HttpMessageConverter<?>> converters=new ArrayList<HttpMessageConverter<?>>(7);
        converters.add(converter1);
        converters.add(converter2);
        converters.add(converter3);
        converters.add(converter4);
        converters.add(converter5);
        converters.add(converter6);
        converters.add(converter7);
        template.setMessageConverters(converters);
        return template;
    }
}
