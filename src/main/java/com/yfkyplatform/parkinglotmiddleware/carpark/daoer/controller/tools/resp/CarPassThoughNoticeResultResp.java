package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 出入场通知地址生成结果
 *
 * @author Suhuyuan
 */
@Data
public class CarPassThoughNoticeResultResp {

    @ApiModelProperty(value = "入场通知")
    private String carIn;

    @ApiModelProperty(value = "出场通知")
    private String carOut;
}
