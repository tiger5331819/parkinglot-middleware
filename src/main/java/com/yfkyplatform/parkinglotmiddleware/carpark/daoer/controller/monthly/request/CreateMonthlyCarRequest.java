package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建月租车请求
 *
 * @author Suhuyuan
 */
@Data
public class CreateMonthlyCarRequest {

    @ApiModelProperty(value = "卡类型编号：11~18:月租车A~H,41~42:免费车A~B,51~58:储值车A~H", required = true)
    private Integer cardTypeId;

    @ApiModelProperty(value = "月租开始时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String startTime;

    @ApiModelProperty(value = "月租结束时间 yyyy-MM-dd HH:mm:ss", required = true)
    private String endTime;

    @ApiModelProperty(value = "收费金额", required = true)
    private String balanceMoney;

    @ApiModelProperty(value = "0 现金 1微信 2支付宝", required = true)
    private int payType;

    @ApiModelProperty(value = "人员名称", required = true)
    private String contactName;

    @ApiModelProperty(value = "人员手机号码", required = true)
    private String contactPhone;
}
