package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carfee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 临停车缴费请求
 *
 * @author Suhuyuan
 */
@Data
public class CarFeePayWithArrearRequest {
    @Schema(description =  "车牌", required = true)
    private String carNo;
    @Schema(description =  "入场时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String entryTime;
    @Schema(description =  "支付时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String payTime;
    @Schema(description =  "停车时长 单位：分钟", required = true)
    private int duration;
    @Schema(description =  "应收金额 单位：元", required = true)
    private BigDecimal totalAmount;
    @Schema(description =  "优惠金额 单位：元", required = true)
    private BigDecimal disAmount;
    @Schema(description =  "0 出口缴费 1 定点缴费 2 超时缴费", required = true)
    private int paymentType;
    @Schema(description =  "支付类型0，现金支付，1微信支付,2，支付宝支付,3,银联闪付", required = true)
    private int payType;
    @Schema(description =  "支付订单编号 唯一", required = true)
    private String paymentTnx;
    @Schema(description =  "实收金额,单位：元", required = true)
    private BigDecimal couponAmount;
    @Schema(description =  "通道ID", required = true)
    private String channelId;
    @Schema(description =  "入场记录ID", required = true)
    private String inId;
    @Schema(description =  "车场ID", required = true)
    private String parkNo;
}
