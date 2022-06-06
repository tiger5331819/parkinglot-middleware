package com.yfkyplatform.parkinglot.carpark.daoer.controller.guest.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建访客请求
 *
 * @author Suhuyuan
 */
@Data
public class CreateGuestRequest {
    @ApiModelProperty(value = "访客名称",required = true)
    private String guestName;

    @ApiModelProperty(value = "车牌号", required = true)
    private String carNo;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "访问时间", required = true)
    private String visitTime;

    @ApiModelProperty(value = "访问理由")
    private String description;
}
