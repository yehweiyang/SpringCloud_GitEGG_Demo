package com.weiyang.gitegg.service.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * gitegg-system 啟動類
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.weiyang")
@EnableDiscoveryClient
public class GitEggSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitEggSystemApplication.class,args);
    }

}
