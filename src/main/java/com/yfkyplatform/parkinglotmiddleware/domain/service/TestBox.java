package com.yfkyplatform.parkinglotmiddleware.domain.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 月卡车费用修改
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

    public BigDecimal changeFee(BigDecimal fee) {
        if (boxEnable()) {
            return env.getProperty(prefix + "." + "newFee", BigDecimal.class);
        } else {
            return fee;
        }
    }
}
