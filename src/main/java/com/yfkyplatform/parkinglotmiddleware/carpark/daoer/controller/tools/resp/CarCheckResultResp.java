package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 车辆在场判断
 *
 * @author Suhuyuan
 */
@Data
public class CarCheckResultResp {

    @Schema(name =  "车牌号码")
    private String carNo;

    @Schema(name =  "车辆是否在场")
    private Boolean in;
}
