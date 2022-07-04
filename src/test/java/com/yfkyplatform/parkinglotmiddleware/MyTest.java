package com.yfkyplatform.parkinglotmiddleware;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Suhuyuan
 */
@SpringBootTest
public class MyTest {

    @ParameterizedTest
    @CsvSource({
            "KR148",
            "KR149",
            "KR159"
    })
    void parameterTest(String carNo) {
        System.out.println("粤B" + carNo);
    }
}
