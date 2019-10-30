package com.sun.snow.module;

import com.sun.snow.redis.StringRedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lish
 * @Description:
 * @Date: Create in 2019/7/12
 */
@RestController
public class HelloController {

    @Autowired
    StringRedisServiceImpl stringRedisService;

    @GetMapping
    public String sayHello(){
        return "bookForm";
    }

    @PostMapping("/redis")
    public String testRedis(@RequestParam("key") String key,@RequestParam("value") String value){
        stringRedisService.set(key,value,2000l);
        return stringRedisService.get(key).toString();
    }

}
