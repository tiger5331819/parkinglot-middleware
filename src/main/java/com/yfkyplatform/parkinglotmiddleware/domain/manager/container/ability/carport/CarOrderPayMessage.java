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
     * 停车时长
     */
    int serviceTime;
    /**
     * 应缴金额,单位元
     */
    BigDecimal totalFee;
    /**
     * 优惠金额,单位元
     */
    BigDecimal discountFee;
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
    /**
     * 通道ID
     */
    String channelId;
}
