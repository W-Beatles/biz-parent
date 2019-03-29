package com.waynechu.renting.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhuwei
 * @date 2019/1/2 9:42
 */
@EnableSwagger2
@Configuration
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "dev")
public class SwaggerConfig {
    private static final String API_TITLE = "房源API";
    private static final String API_DESCRIPTION = "房源信息接口";
    private static final String API_VERSION = "1.0.0";
    private static final String SWAGGER_SCAN_BASE_PACKAGE = "cn.waynechu.renting.web.controller";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                //.termsOfServiceUrl("API terms of service")
                .contact(new Contact("Wayne Chu", "waynechu.cn", "contact@waynechu.cn"))
                .version(API_VERSION)
                .build();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }
}
