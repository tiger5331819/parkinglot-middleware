package com.yfkyplatform.parkinglotmiddleware.api.web;

import com.yfkyframework.common.core.domain.BaseRpcReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 车辆订单缴费信息
 *
 * @author Suhuyuan
 */
@Data
public class OrderPayMessage extends BaseRpcReq {
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    String payTime;
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
    @ApiModelProperty(value = "支付类型")
    int payType;
    /**
     * 支付流水号
     */
    @ApiModelProperty(value = "支付流水号")
    String paymentTransactionId;
    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    BigDecimal payFee;
    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    BigDecimal discountFee;
}
