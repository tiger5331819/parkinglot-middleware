package com.yfkyplatform.parkinglotmiddleware.universal;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

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
        rocketMQTemplate.syncSendOrderly("TEST", carNo, UUID.randomUUID().toString());
    }


    @Test
    void pullTest() {
        Object obj = rocketMQTemplate.receive(String.class);

        System.out.println();
    }

    @Test
    void asyncSendTest() {
        rocketMQTemplate.asyncSend("parkingLot-carIn", "213", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
        System.out.println();
    }
}
