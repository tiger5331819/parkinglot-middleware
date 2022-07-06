package com.yfkyplatform.parkinglotmiddleware;

import com.yfkyplatform.parkinglotmiddleware.domain.service.ParkingLotManagerEnum;
import org.junit.Test;
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

    @Test
    public void Test1() {
        ParkingLotManagerEnum data = ParkingLotManagerEnum.ValueOf((Integer) null);
        switch (data) {

            case Daoer:
                break;
            default:
                System.out.println("123");
                break;
        }
    }
}
