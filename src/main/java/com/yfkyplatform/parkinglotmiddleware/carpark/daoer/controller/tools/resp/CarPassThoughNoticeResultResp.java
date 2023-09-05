package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 出入场通知地址生成结果
 *
 * @author Suhuyuan
 */
@Data
public class CarPassThoughNoticeResultResp {

    @Schema(title =  "入场通知")
    private String carIn;

    @Schema(title =  "出场通知")
    private String carOut;
}
