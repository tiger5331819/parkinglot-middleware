package com.yfkyplatform.parkinglotmiddleware;

import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author Suhuyuan
 */

@EnableDubbo
@SpringBootApplication
public class ParkinglotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkinglotServiceApplication.class, args);
    }


    @Bean
    public ApplicationRunner runner(ApplicationContext ctx) {
        return args -> {

            ParkingLotManagerFactory factory = ctx.getBean(ParkingLotManagerFactory.class);
            factory.getManagerSupport();
        };
    }
}
