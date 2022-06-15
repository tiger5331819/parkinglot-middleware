package com.yfkyplatform.parkinglotmiddleware.universal;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Suhuyuan
 */
@SpringBootTest
public class RocketMQTest {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @ParameterizedTest
    @CsvSource({
            "KR148",
            "KR149",
            "KR159"
    })
    void syncSendOrderlyTest(String carNo){
        rocketMQTemplate.syncSendOrderly("TEST",carNo, UUID.randomUUID().toString());
    }


    @Test
    void pullTest(){
        Object obj=rocketMQTemplate.receive(String.class);

        System.out.println();
    }
}
