package com.yfkyplatform.parkinglot.api.web.carport.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 无牌车入场请求
 *
 * @author Suhuyuan
 */
@Data
public class BlankCarRequest {
    @ApiModelProperty(value = "微信或支付宝openid",required = true)
    private String openId;
    @ApiModelProperty(value = "1微信2支付宝",required = true)
    private int scanType;
    @ApiModelProperty(value = "通道号",required = true)
    private String channelId;
}
