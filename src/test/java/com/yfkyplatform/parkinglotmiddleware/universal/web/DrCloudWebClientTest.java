package com.yfkyplatform.parkinglotmiddleware.universal.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglotmiddleware.universal.testbox.TestBox;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DrCloudWebClientTest {

    @Autowired
    TestBox testBox;

    @Test
    void getToken() {
        String token = testBox.drCloudClient().getToken();
        assertNotNull(token);
        System.out.println(token);
    }

    @Test
    void getregist() throws JsonProcessingException {
        String data = "{\"pageNum\":1,\"pageSize\":10,\"parkName\":\"金\"}";
        Map<String, Object> result = testBox.drCloudClient().post(data, "api/backstage/regist/findall");
        System.out.println();
    }
}