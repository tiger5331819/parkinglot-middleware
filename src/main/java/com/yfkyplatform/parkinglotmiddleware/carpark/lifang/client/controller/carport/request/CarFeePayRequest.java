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
    @Schema(name =  "车牌", required = true)
    private String carNo;
    @Schema(name =  "支付时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String payTime;
    @Schema(name =  "应收金额 单位：元", required = true)
    private BigDecimal totalAmount;
    @Schema(name =  "优惠金额 单位：元", required = true)
    private BigDecimal disAmount;
    @Schema(name =  "实收金额,单位：元", required = true)
    private BigDecimal couponAmount;
}
