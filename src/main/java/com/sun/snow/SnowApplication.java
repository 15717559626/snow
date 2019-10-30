package com.sun.snow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@MapperScan("com.sun.snow.module.*.mapper")
@EnableWebSecurity
public class SnowApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(SnowApplication.class, args);
    }

}
