package com.parkinglotmiddleware.universal.testbox;

import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

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

    public Boolean enable() {
        return env.getProperty(prefix + "enable", Boolean.class, false);
    }

    /**
     * 费用修改
     *
     * @param changeFunc 费用修改函数
     * @return
     */
    public void ifCanChange(BiConsumer<BigDecimal, BigDecimal> changeFunc) {
        if (enable()) {
            BigDecimal changeFee = env.getProperty(prefix + "newFee", BigDecimal.class, new BigDecimal(-1));
            BigDecimal discountFee = env.getProperty(prefix + "discountFee", BigDecimal.class, new BigDecimal(-1));
            changeFunc.accept(changeFee, discountFee);
        }
    }
}
