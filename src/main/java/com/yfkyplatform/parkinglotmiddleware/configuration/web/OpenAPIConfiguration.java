package com.yfkyplatform.parkinglotmiddleware.configuration.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author suhuyuan
 *
 */
@Configuration
public class OpenAPIConfiguration {

    @Value("${app.version}")
    private String version;

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
                .info(new Info().title("停车场中间件")
                        .contact(new Contact()
                                .name("苏琥元")
                                .email("suhuyuan@outlook.com"))
                        .description("上下文感知的停车场物联网服务")
                        .version(version));
    }
}

