package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.guest.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 创建访客请求
 *
 * @author Suhuyuan
 */
@Data
public class CreateGuestRequest {
    @Schema(title =  "访客名称",required = true)
    private String guestName;

    @Schema(title =  "车牌号", required = true)
    private String carNo;

    @Schema(title =  "手机号")
    private String phone;

    @Schema(title =  "访问时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visitTime;

    @Schema(title =  "访问理由")
    private String description;
}
