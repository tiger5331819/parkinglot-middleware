package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 月租车续期请求
 *
 * @author Suhuyuan
 */
@Data
public class RenewalMonthlyCarRequest {
    @Schema(name =  "月租开始时间 yyyy-MM-dd HH:mm:ss",required = true)
    private String newStartTime;
    @Schema(name =  "月租结束时间 yyyy-MM-dd HH:mm:ss",required = true)
    private String newEndTime;
    @Schema(name =  "收费金额",required = true)
    private String balanceMoney;
    @Schema(name =  "0 现金 1微信 2支付宝",required = true)
    private int payType;
}
