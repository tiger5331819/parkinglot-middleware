package com.parkinglotmiddleware.carpark.daoer.controller.carport.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 控制通道请求
 *
 * @author Suhuyuan
 */
@Data
public class DueCarSuccessRequest {
    @Schema(description =  "通道ID")
    private String channelId;
    @Schema(description =  "车牌号")
    private String carNo;
}
