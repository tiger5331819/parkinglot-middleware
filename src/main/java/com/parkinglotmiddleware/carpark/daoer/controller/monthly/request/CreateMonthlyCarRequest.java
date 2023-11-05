package com.parkinglotmiddleware.carpark.daoer.controller.monthly.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建月租车请求
 *
 * @author Suhuyuan
 */
@Data
public class CreateMonthlyCarRequest {

    @Schema(description =  "卡类型编号：11~18:月租车A~H,41~42:免费车A~B,51~58:储值车A~H", required = true)
    private Integer cardTypeId;

    @Schema(description =  "月租开始时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String startTime;

    @Schema(description =  "月租结束时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String endTime;

    @Schema(description =  "收费金额", required = true)
    private String balanceMoney;

    @Schema(description =  "0 现金 1微信 2支付宝", required = true)
    private int payType;

    @Schema(description =  "人员名称", required = true)
    private String contactName;

    @Schema(description =  "人员手机号码", required = true)
    private String contactPhone;
}
