package com.yfkyplatform.parkinglotmiddleware.universal;

import cn.hutool.json.JSONUtil;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResult;
import com.parkinglotmiddleware.universal.RedisTool;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
    public void ttt() {
        String jsonStr = redis.get("order:daoer:fee:粤SP8M98");
        CarFeeResult fee = JSONUtil.toBean(jsonStr, CarFeeResult.class);
        System.out.println(fee);
    }
    @ParameterizedTest
    @CsvSource({
            "KR148",
            "KR149",
            "KR159"
    })
    public void hSetTest(String carNo) {
        HashMap<String, String> carNoMap = new LinkedHashMap<>();
        carNoMap.put(String.valueOf(carNo.hashCode()), carNo);
        redis.hash().putAll("粤B", carNoMap);
    }

    @ParameterizedTest
    @CsvSource({
            "KR148",
            "KR149",
            "KR159"
    })
    public void hGetTest(String carNo) {
        System.out.println(redis.hash().hasKey("粤B", String.valueOf(carNo.hashCode())));
        System.out.println(redis.hash().get("粤B", String.valueOf(carNo.hashCode())));
    }

    @ParameterizedTest
    @CsvSource({
            "KR148",
            "KR149",
            "KR159"
    })
    public void redisTest(String carNo) {
        redis.set("粤B" + carNo.hashCode(), carNo);
    }
}
