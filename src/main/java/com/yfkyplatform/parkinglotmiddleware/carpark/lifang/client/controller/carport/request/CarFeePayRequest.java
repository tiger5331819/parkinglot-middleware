package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.controller.carport.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 临停车缴费请求
 *
 * @author Suhuyuan
 */
@Data
public class CarFeePayRequest {
    @Schema(title =  "车牌", required = true)
    private String carNo;
    @Schema(title =  "支付时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String payTime;
    @Schema(title =  "应收金额 单位：元", required = true)
    private BigDecimal totalAmount;
    @Schema(title =  "优惠金额 单位：元", required = true)
    private BigDecimal disAmount;
    @Schema(title =  "实收金额,单位：元", required = true)
    private BigDecimal couponAmount;
}
