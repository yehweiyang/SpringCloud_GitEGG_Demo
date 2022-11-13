package com.weiyang.gitegg.service.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * gitegg-base 启动类
 */
@ComponentScan(basePackages = "com.weiyang")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.weiyang")
@SpringBootApplication
@MapperScan("com.weiyang")
public class GitEggBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitEggBaseApplication.class,args);
    }

}

