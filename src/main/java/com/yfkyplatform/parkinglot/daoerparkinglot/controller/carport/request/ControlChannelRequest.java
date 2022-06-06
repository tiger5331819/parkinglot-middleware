package com.yfkyplatform.parkinglot.daoerparkinglot.controller.carport.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 控制通道请求
 *
 * @author Suhuyuan
 */
@Data
public class ControlChannelRequest {
    @ApiModelProperty(value = "通道ID",required = true)
    private String channelId;
    @ApiModelProperty(value = "1开启 2 关闭",required = true)
    private int channelIdStatus;
}
