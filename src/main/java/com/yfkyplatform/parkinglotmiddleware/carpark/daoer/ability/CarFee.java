package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 费用
 *
 * @author Suhuyuan
 */
@Data
public class CarFee {
    /**
     * 实收金额,单位元
     */
    private BigDecimal payCharge;

    /**
     * 应收金额,单位元
     */
    private BigDecimal amount;

    /**
     * 优惠金额,单位元
     */
    private BigDecimal discountAmount;
}
