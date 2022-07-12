package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.controller.carport.request;

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
    @ApiModelProperty(value = "支付时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String payTime;
    @ApiModelProperty(value = "应收金额 单位：元", required = true)
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "优惠金额 单位：元", required = true)
    private BigDecimal disAmount;
    @ApiModelProperty(value = "实收金额,单位：元", required = true)
    private BigDecimal couponAmount;
}
