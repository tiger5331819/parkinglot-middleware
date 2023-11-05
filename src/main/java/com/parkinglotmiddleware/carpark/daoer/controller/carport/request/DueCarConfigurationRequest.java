package com.parkinglotmiddleware.carpark.daoer.controller.carport.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

/**
 * 控制通道请求
 *
 * @author Suhuyuan
 */
@Data
public class DueCarConfigurationRequest {

    @Schema(description =  "是否不可进 0不可进，1可进")
    private Integer urgepayNotIn;

    @Schema(description =  "是否不可出 0不可出，1可出")
    private Integer urgepayNotOut;

    @Schema(description =  "生效开始时间HH:mm:ss")
    private LocalTime startTime;

    @Schema(description =  "生效结束时间 HH:mm:ss")
    private LocalTime closeTime;
}
