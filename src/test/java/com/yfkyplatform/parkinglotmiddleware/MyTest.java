package com.yfkyplatform.parkinglotmiddleware;

import com.yfkyplatform.parkinglotmiddleware.domain.service.ParkingLotManagerEnum;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

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
        ParkingLotManagerEnum data = ParkingLotManagerEnum.fromCode(null);
        if (Objects.requireNonNull(data) == ParkingLotManagerEnum.Daoer) {
        } else {
            System.out.println("123");
        }
    }

    @ParameterizedTest
    @CsvSource({"b8754039bbf911edad48000c29bdcc13",
            "c7200e02bbfa11edad48000c29bdcc13",
            "f2ec6b24bbff11edad48000c29bdcc13"})
    void ttttt(String tt) {
        byte[] bs = tt.getBytes();
        long value = 0;
        for (int count = 0; count < 12; ++count) {
            int shift = count << 3;
            value |= ((long) 255 << shift) & ((long) bs[count] << shift);
        }
        System.out.println("1结果：" + value);

        for (int count = 0; count < 12; ++count) {

            value |= bs[count];
        }
        System.out.println("2结果：" + value);

        for (int count = 0; count < 12; ++count) {

            value &= bs[count];
        }
        System.out.println("3结果：" + value);
    }
}
