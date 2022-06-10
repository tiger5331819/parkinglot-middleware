package com.yfkyplatform.parkinglot.universal;

import com.yfkyplatform.parkinglot.configuartion.redis.RedisTool;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.IOException;
import java.util.*;

/**
 * @author Suhuyuan
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTool redis;

    @ParameterizedTest
    @CsvSource({
            "KR148",
            "KR149",
            "KR159"
    })
    void hSetTest(String carNo){
        HashMap<String,String> carNoMap=new LinkedHashMap<>();
        carNoMap.put(String.valueOf(carNo.hashCode()),carNo);
        redis.hash().putAll("粤B",carNoMap);
    }

    @ParameterizedTest
    @CsvSource({
            "KR148",
            "KR149",
            "KR159"
    })
    void hGetTest(String carNo){
        System.out.println(redis.hash().hasKey("粤B",String.valueOf(carNo.hashCode())));
        System.out.println(redis.hash().get("粤B",String.valueOf(carNo.hashCode())));
    }

    @ParameterizedTest
    @CsvSource({
            "KR148",
            "KR149",
            "KR159"
    })
    void redisTest(String carNo){
        redis.set("粤B"+carNo.hashCode(),carNo);
    }

    @Test
    void TTTT(){
        System.out.println("123".hashCode());
        System.out.println("1234".hashCode());
        System.out.println("1234东莞".hashCode());
        System.out.println("1234南城".hashCode());
        System.out.println("123456789东莞".hashCode());
        System.out.println("123456789东莞".hashCode());
    }
}
