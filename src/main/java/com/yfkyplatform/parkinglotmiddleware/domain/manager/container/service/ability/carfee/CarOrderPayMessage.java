package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private String carNo;
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
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
    private int payType;
    /**
     * 支付流水号
     */
    private String paymentTransactionId;
    /**
     * 优惠金额,单位元
     */
    private BigDecimal discountFee;
    /**
     * 支付金额
     */
    private BigDecimal payFee;

    /**
     * 入场时间
     */
    private LocalDateTime inTime;

    /**
     * 订单创建时间（计费时间） yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime createTime;

    /**
     * 入场记录ID
     */
    private String inId;

    /**
     * 通道ID
     */
    private String channelId;
}
