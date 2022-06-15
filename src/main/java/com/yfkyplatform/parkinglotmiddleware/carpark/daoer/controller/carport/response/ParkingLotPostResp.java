package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 停车场推送数据返回结果
 *
 * @author Suhuyuan
 */
@Data
public class ParkingLotPostResp {
    @ApiModelProperty(value = "0失败 1成功")
    private String status="1";
    private String msg="success";
}
