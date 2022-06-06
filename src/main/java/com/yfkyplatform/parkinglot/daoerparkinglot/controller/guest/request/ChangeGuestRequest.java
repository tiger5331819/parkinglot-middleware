package com.yfkyplatform.parkinglot.daoerparkinglot.controller.guest.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建访客请求
 *
 * @author Suhuyuan
 */
@Data
public class ChangeGuestRequest {
    @ApiModelProperty(value = "访客名称")
    private String guestName;

    @ApiModelProperty(value = "唯一记录标识", required = true)
    private String objectId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "访问时间")
    private String visitTime;

    @ApiModelProperty(value = "访问理由")
    private String description;
}
