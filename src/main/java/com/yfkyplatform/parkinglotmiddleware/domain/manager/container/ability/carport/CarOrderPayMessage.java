package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 车辆订单缴费信息
 *
 * @author Suhuyuan
 */
@Data
public class CarOrderPayMessage {
    /**
     * 车牌号
     */
    String carNo;
    /**
     * 支付时间
     */
    String payTime;
    /**
     * 支付模式类型
     */
    int paymentType;
    /**
     * 支付类型
     */
    int payType;
    /**
     * 支付流水号
     */
    String paymentTransactionId;
    /**
     * 支付金额
     */
    BigDecimal payFee;
}
