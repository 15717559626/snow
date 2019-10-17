package com.sun.snow;

import com.sun.snow.redis.StringRedisServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void test(){
        stringRedisService.set("testKey","1111");
        System.out.println(stringRedisService.get("testKey"));
    }
}
