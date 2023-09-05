package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 控制通道请求
 *
 * @author Suhuyuan
 */
@Data
public class ControlChannelRequest {
    @Schema(title =  "通道ID",required = true)
    private String channelId;
    @Schema(title =  "1开启 2 关闭",required = true)
    private int channelIdStatus;
}
