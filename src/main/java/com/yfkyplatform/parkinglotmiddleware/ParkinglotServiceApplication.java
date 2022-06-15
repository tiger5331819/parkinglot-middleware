package com.yfkyplatform.parkinglotmiddleware;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Suhuyuan
 */
@EnableDiscoveryClient
@EnableDubbo
@SpringBootApplication
public class ParkinglotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkinglotServiceApplication.class, args);
    }

}
