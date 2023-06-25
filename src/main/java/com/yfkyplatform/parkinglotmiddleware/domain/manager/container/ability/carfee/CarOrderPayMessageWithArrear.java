package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 车辆订单缴费信息（欠费）
 *
 * @author Suhuyuan
 */
@Data
public class CarOrderPayMessageWithArrear {

    /**
     * 车牌号
     */
    String carNo;

    /**
     * 支付时间
     */
    LocalDateTime payTime;

    /**
     * 支付类型
     * 0 缺省
     * 1 钱包支付
     * 2 现金支付,
     * 1000 积分支付
     * 2000 微信支付（主）
     * 2001 微信支付（被）
     * 3000 支付宝支付
     * 3001 支付宝支付（被）
     * 4000 云闪付支付
     * 5000 东莞通支付
     */
    int payType;

    /**
     * 支付流水号
     */
    String paymentTransactionId;

    /**
     * 优惠金额,单位元
     */
    BigDecimal discountFee;

    /**
     * 支付金额
     */
    BigDecimal payFee;

    /**
     * 入场记录ID
     */
    String inId;
}
