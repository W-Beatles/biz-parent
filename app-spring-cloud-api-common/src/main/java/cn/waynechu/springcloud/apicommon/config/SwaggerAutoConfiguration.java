package cn.waynechu.springcloud.apicommon.config;

import cn.waynechu.springcloud.apicommon.properties.SwaggerProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/2 9:42
 */
@EnableSwagger2
@Configuration
@EnableConfigurationProperties({SwaggerProperty.class})
@ConditionalOnProperty(value = SwaggerProperty.SWAGGER_CONFIG_PREFIX + ".enable", havingValue = "true")
public class SwaggerAutoConfiguration {

    @Autowired
    private SwaggerProperty swaggerProperty;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperty.getApiTitle())
                .description(swaggerProperty.getApiDescription())
                //.termsOfServiceUrl("API terms of service")
                .contact(new Contact(swaggerProperty.getContactName(), swaggerProperty.getContactUrl(), swaggerProperty.getContactEmail()))
                .version(swaggerProperty.getApiVersion())
                .build();
    }

    @Bean
    public Docket createRestApi() {
        // 添加公共head参数
        ParameterBuilder tokenPar = new ParameterBuilder();
        ParameterBuilder deviceIdPar = new ParameterBuilder();
        ParameterBuilder channelPar = new ParameterBuilder();
        ParameterBuilder apiVersionPar = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        tokenPar.name("user").description("用户").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).order(0).build();
        deviceIdPar.name("deviceId").description("设备ID").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).order(1).build();
        channelPar.name("channel").description("渠道").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).order(2).build();
        apiVersionPar.name("apiVersion").description("版本").defaultValue("1.0").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).order(3).build();
        parameters.add(tokenPar.build());
        parameters.add(deviceIdPar.build());
        parameters.add(channelPar.build());
        parameters.add(apiVersionPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperty.getScanPackage()))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }
}
