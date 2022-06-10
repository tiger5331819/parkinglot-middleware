package com.yfkyplatform.parkinglot.api.dubbo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("dev")
public class TestServiceTest {
    @DubboReference
    private ITestService ttt;

    @Test
    void dubboTest(){
        int result=ttt.Add(2003001,1);
        assertNotNull(result);
    }
}