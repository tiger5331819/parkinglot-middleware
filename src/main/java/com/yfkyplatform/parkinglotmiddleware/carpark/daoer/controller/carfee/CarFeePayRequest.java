package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carfee;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 临停车缴费请求
 *
 * @author Suhuyuan
 */
@Data
public class CarFeePayRequest {
    @ApiModelProperty(value = "车牌", required = true)
    private String carNo;
    @ApiModelProperty(value = "入场时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String entryTime;
    @ApiModelProperty(value = "支付时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String payTime;
    @ApiModelProperty(value = "停车时长 单位：分钟", required = true)
    private int duration;
    @ApiModelProperty(value = "应收金额 单位：元", required = true)
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "优惠金额 单位：元", required = true)
    private BigDecimal disAmount;
    @ApiModelProperty(value = "0 出口缴费 1 定点缴费 2 超时缴费", required = true)
    private int paymentType;
    @ApiModelProperty(value = "支付类型0，现金支付，1微信支付,2，支付宝支付,3,银联闪付", required = true)
    private int payType;
    @ApiModelProperty(value = "支付订单编号 唯一", required = true)
    private String paymentTnx;
    @ApiModelProperty(value = "实收金额,单位：元", required = true)
    private BigDecimal couponAmount;
    @ApiModelProperty(value = "通道ID", required = true)
    private String channelId;
}
