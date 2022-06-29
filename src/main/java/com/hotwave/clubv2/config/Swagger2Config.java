package com.hotwave.clubv2.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description Swagger2Config项目文档配置
 * @date 2022-06-29 21:39:00
 */
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com/hotwave/clubv2/controller")).paths(PathSelectors.any()).build();
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("clubv2-Swagger文档").description("项目API接口文档").version("V1.0").termsOfServiceUrl("https://baidu.com").
                contact(new Contact("clubv2","irving7758520@163.com","irving7758520@163.com")).license( "Apache").licenseUrl("Apache").build();
    }
}
