package com.yfkyplatform.parkinglot.api.web.carport.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 车辆订单缴费信息
 *
 * @author Suhuyuan
 */
@Data
public class OrderPayMessage {
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间",required = true)
    String payTime;
    /**
     * 停车时长
     */
    @ApiModelProperty(value = "停车时长",required = true)
    int serviceTime;
    /**
     * 应缴金额,单位元
     */
    @ApiModelProperty(value = "应缴金额,单位元",required = true)
    double totalFee;
    /**
     * 优惠金额,单位元
     */
    @ApiModelProperty(value = "优惠金额,单位元",required = true)
    double discountFee;
    /**
     * 支付模式类型
     */
    @ApiModelProperty(value = "支付模式类型",required = true)
    int paymentType;
    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型",required = true)
    int payType;
    /**
     * 支付流水号
     */
    @ApiModelProperty(value = "支付流水号",required = true)
    String paymentTransactionId;
    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额",required = true)
    double payFee;
    /**
     * 通道ID
     */
    @ApiModelProperty(value = "通道ID",required = false)
    String channelId;
}
