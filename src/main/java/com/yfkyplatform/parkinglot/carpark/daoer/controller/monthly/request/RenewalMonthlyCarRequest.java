package com.yfkyplatform.parkinglot.carpark.daoer.controller.monthly.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 月租车续期请求
 *
 * @author Suhuyuan
 */
@Data
public class RenewalMonthlyCarRequest {
    @ApiModelProperty(value = "月租开始时间 yyyy-MM-dd HH:mm:ss",required = true)
    private String newStartTime;
    @ApiModelProperty(value = "月租结束时间 yyyy-MM-dd HH:mm:ss",required = true)
    private String newEndTime;
    @ApiModelProperty(value = "收费金额",required = true)
    private String balanceMoney;
    @ApiModelProperty(value = "0 现金 1微信 2支付宝",required = true)
    private int payType;
}
