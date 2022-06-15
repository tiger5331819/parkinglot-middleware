package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.guest.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visitTime;

    @ApiModelProperty(value = "访问理由")
    private String description;
}
