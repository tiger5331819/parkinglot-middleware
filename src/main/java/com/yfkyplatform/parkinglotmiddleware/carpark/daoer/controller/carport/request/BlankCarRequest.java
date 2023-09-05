package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 无牌车入场请求
 *
 * @author Suhuyuan
 */
@Data
public class BlankCarRequest {
    @Schema(title =  "微信或支付宝openid",required = true)
    private String openId;
    @Schema(title =  "1微信2支付宝",required = true)
    private int scanType;
    @Schema(title =  "通道号",required = true)
    private String channelId;
}
