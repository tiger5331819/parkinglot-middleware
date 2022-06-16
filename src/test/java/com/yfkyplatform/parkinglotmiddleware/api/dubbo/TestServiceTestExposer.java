package com.yfkyplatform.parkinglotmiddleware.api.dubbo;

import com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.ITestService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ActiveProfiles("dev")
public class TestServiceTestExposer {
    @DubboReference
    private ITestService ttt;
    @Autowired
    private ITestService ttt2;

    @Test
    void dubboTest() {
        int result = ttt.add(2003001, 1);
        assertNotNull(result);
    }

    @Test
    void dubboTest2(){
        int result=ttt2.add(2003001,1);
        assertNotNull(result);
    }

    @Test
    void dubboSetTest(){
        Set result=ttt.add("Hello world");
        assertNotNull(result);
        assertNotEquals(0,result.size());
        result.forEach(item->{
            System.out.println(item);
        });
    }

    @Test
    void dubboExceptionTest(){
        try{
            ttt.exception();
        }catch (Throwable ex){
            System.out.println(ex);
        }

    }
}