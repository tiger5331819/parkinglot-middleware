package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增修改特殊车辆请求
 *
 * @author Suhuyuan
 */
@Data
public class SpecialCarRequest {
    @ApiModelProperty(value = "1黑名单/2特殊车辆", required = true)
    private int carNoType;
    @ApiModelProperty(value = "通行类型 类型(1禁止通行,2通行免费,3自由通行)", required = true)
    private int isStop;
    @ApiModelProperty(value = "收费金额")
    private String description;
    @ApiModelProperty(value = "唯一表示，修改必传")
    private String objectId;
}
