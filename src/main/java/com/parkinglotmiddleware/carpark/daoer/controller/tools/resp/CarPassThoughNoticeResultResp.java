package com.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 出入场通知地址生成结果
 *
 * @author Suhuyuan
 */
@Data
public class CarPassThoughNoticeResultResp {

    @Schema(description =  "入场通知")
    private String carIn;

    @Schema(description =  "出场通知")
    private String carOut;
}
