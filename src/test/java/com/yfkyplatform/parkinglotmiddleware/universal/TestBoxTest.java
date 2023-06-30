package com.yfkyplatform.parkinglotmiddleware.universal;

import com.yfkyplatform.parkinglotmiddleware.universal.testbox.TestBox;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

/**
 * 测试盒测试
 *
 * @author Suhuyuan
 */
@ActiveProfiles("dev")
@SpringBootTest
public class TestBoxTest {
    @Autowired
    private TestBox testBox;

    @Test
    void Test1() {
        BigDecimal newFee = testBox.changeFee().change(new BigDecimal(100));
        System.out.println(newFee);
    }
}
