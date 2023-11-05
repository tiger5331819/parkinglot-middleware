package com.parkinglotmiddleware.carpark.daoer.controller.guest.request;

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
    @Schema(description =  "访客名称",required = true)
    private String guestName;

    @Schema(description =  "车牌号", required = true)
    private String carNo;

    @Schema(description =  "手机号")
    private String phone;

    @Schema(description =  "访问时间", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visitTime;

    @Schema(description =  "访问理由")
    private String description;
}
