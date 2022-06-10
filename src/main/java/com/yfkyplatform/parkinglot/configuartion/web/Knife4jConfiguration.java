package com.yfkyplatform.parkinglot.configuartion.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhuquan
 * @date 2021-07-28 10:01
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "daoerProxyAPI")
    public Docket daoerProxyAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                // Api文档基础信息
                .apiInfo(new ApiInfoBuilder()
                        .title("道尔云API")
                        .description("道尔云代理API列表")
                        .build())
                // 全局参数
                .globalOperationParameters(globalOperationParameters())
                // 该Api文档分组名称
                .groupName("道尔云API代理")
                .enable(true)
                // 切换到select构建器
                .select()
                // 需要扫描的Controller包路径
                .apis(RequestHandlerSelectors.basePackage("com.yfkyplatform.parkinglot.carpark.daoer"))
                // 需要生效的uri路径
                .paths(PathSelectors.any())
                .build();
    }

    @Bean(value = "defaultApiV2")
    public Docket defaultApiV2() {
        return new Docket(DocumentationType.SWAGGER_2)
                // Api文档基础信息
                .apiInfo(new ApiInfoBuilder()
                        .title("停车场API")
                        .description("Knife4j Description")
                        .build())
                // 全局参数
                .globalOperationParameters(globalOperationParameters())
                // 该Api文档分组名称
                .groupName("停车场业务")
                .enable(true)
                // 切换到select构建器
                .select()
                // 需要扫描的Controller包路径
                .apis(RequestHandlerSelectors.basePackage("com.yfkyplatform.parkinglot.api.web"))
                // 需要生效的uri路径
                .paths(PathSelectors.any())
                .build();
    }

    private List<Parameter> globalOperationParameters() {
        List<Parameter> parameters = new ArrayList<>();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        Parameter authorizationParameter = parameterBuilder
                .name("Authorization")
                .description("Bearer Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
        parameters.add(authorizationParameter);
        return parameters;
    }
}

