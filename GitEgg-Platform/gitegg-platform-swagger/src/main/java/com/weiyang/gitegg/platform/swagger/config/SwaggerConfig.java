package com.weiyang.gitegg.platform.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    @Bean(value = "GitEggApi")
    public Docket GitEggApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分組名稱
                .groupName("2.X123456版本")
                .select()
                //這裡指定Controller掃描包路徑
//                .apis(RequestHandlerSelectors.basePackage("com.weiyang.gitegg.service.system.controller"))
                .apis(RequestHandlerSelectors.basePackage("com.weiyang"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().version("1.0.0")
                .title("Spring Cloud Swagger2 文件")
                .description("Spring Cloud Swagger2 文件")
                .termsOfServiceUrl("www.gitegg.com 123456")
                .build();
    }

}
