package com.sun.snow.module;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lish
 * @Description:
 * @Date: Create in 2019/7/12
 */
@RestController
public class HelloController {


    @GetMapping
    public String sayHello(){
        return "bookForm";
    }
}
