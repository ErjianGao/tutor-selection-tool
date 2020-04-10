package com.erjiangao.tutorselectiontool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // 配置了swagger的Docket的Bean实例
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 是否启动
                .enable(true)
                // 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.erjiangao.tutorselectiontool" +
                        ".controller"))
                .build();
    }

    // 配置swagger信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("Erjian Gao", "http://erjiangao.com", "754095810@qq.com");
        return new ApiInfo(
                "API Documentation",
                "API documentation for Graduation Design Tutor Selection Tool project",
                "v0.9",
                "",
                contact,
                "MIT LICENSE",
                "https://opensource.org/licenses/MIT",
                new ArrayList<>()
        );
    }
}
