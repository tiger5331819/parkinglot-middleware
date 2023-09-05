package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建月租车请求
 *
 * @author Suhuyuan
 */
@Data
public class CreateMonthlyCarRequest {

    @Schema(title =  "卡类型编号：11~18:月租车A~H,41~42:免费车A~B,51~58:储值车A~H", required = true)
    private Integer cardTypeId;

    @Schema(title =  "月租开始时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String startTime;

    @Schema(title =  "月租结束时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String endTime;

    @Schema(title =  "收费金额", required = true)
    private String balanceMoney;

    @Schema(title =  "0 现金 1微信 2支付宝", required = true)
    private int payType;

    @Schema(title =  "人员名称", required = true)
    private String contactName;

    @Schema(title =  "人员手机号码", required = true)
    private String contactPhone;
}
