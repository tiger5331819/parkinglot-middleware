package com.yfkyplatform.parkinglotmiddleware.universal;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 测试盒
 *
 * @author Suhuyuan
 */
@Component
public class TestBox {
    private final Environment env;
    private final String prefix = "TestBox";

    public TestBox(Environment env) {
        this.env = env;
    }

    private Boolean boxEnable() {
        return env.getProperty(prefix + "." + "enable", Boolean.class, false);
    }

    /**
     * 费用修改
     *
     * @param fee
     * @return
     */
    public BigDecimal changeFee(BigDecimal fee) {
        if (boxEnable()) {
            return env.getProperty(prefix + "." + "newFee", BigDecimal.class);
        } else {
            return fee;
        }
    }
}