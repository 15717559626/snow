package com.sun.snow;

import com.sun.snow.redis.StringRedisServiceImpl;
import com.sun.snow.util.RestTemplateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: lish
 * @Description:
 * @Date: Create in 2019/9/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    StringRedisServiceImpl stringRedisService;

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Test
    public void test(){
        stringRedisService.set("testKey","1111");
        System.out.println(stringRedisService.get("testKey"));
    }

    @Test
    public void testHttp(){
        String result = restTemplateUtils.post("http://i.itpk.cn/api.php?question=天气&api_key=31984ffe7ee3f1b7f9900a5915d5e509" +
                "&api_secret=y6ia5z1t57wb","", MediaType.APPLICATION_JSON);
        System.out.println(result);
    }
}
