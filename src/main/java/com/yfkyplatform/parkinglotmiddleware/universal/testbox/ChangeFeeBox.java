package com.yfkyplatform.parkinglotmiddleware.universal.testbox;

import org.springframework.core.env.Environment;

import java.math.BigDecimal;

/**
 * 修改费用
 *
 * @author Suhuyuan
 */

public class ChangeFeeBox {

    private final Environment env;
    private final String prefix = "TestBox.changeFee.";

    public ChangeFeeBox(Environment env) {
        this.env = env;
    }

    private Boolean enable() {
        return env.getProperty(prefix + "enable", Boolean.class, false);
    }

    /**
     * 费用修改
     *
     * @param fee
     * @return
     */
    public BigDecimal change(BigDecimal fee) {
        if (enable()) {
            return env.getProperty(prefix + "newFee", BigDecimal.class);
        } else {
            return fee;
        }
    }
}
