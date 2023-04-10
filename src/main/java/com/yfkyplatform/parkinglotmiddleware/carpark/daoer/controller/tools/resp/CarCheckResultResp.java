package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 车辆在场判断
 *
 * @author Suhuyuan
 */
@Data
public class CarCheckResultResp {

    @ApiModelProperty(value = "车牌号码")
    private String carNo;

    @ApiModelProperty(value = "车辆是否在场")
    private Boolean in;
}
