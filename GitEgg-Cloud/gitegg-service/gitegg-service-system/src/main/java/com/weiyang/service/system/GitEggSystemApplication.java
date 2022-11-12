package com.weiyang.service.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * gitegg-system 啟動類
 */
@MapperScan("com.weiyang.*.*.mapper")
@SpringBootApplication
@ComponentScan(basePackages = "com.weiyang")
public class GitEggSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitEggSystemApplication.class,args);
    }

}
