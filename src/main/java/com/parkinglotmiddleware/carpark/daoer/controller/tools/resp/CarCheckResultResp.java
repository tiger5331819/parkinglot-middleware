package com.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 车辆在场判断
 *
 * @author Suhuyuan
 */
@Data
public class CarCheckResultResp {

    @Schema(description =  "车牌号码")
    private String carNo;

    @Schema(description =  "车辆是否在场")
    private Boolean in;
}
