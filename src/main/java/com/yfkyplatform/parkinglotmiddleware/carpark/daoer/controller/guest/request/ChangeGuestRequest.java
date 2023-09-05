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
public class ChangeGuestRequest {
    @Schema(name =  "访客名称")
    private String guestName;

    @Schema(name =  "唯一记录标识", required = true)
    private String objectId;

    @Schema(name =  "手机号")
    private String phone;

    @Schema(name =  "访问时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visitTime;

    @Schema(name =  "访问理由")
    private String description;
}
