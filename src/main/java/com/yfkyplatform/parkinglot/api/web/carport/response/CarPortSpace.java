package com.yfkyplatform.parkinglot.api.web.carport.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 车场余位
 *
 * @author Suhuyuan
 */
@Data
public class CarPortSpace {
    /**
     * 总车位数
     */
    @ApiModelProperty(value = "总车位数")
    private int total;
    /**
     * 空余车位数
     */
    @ApiModelProperty(value = "空余车位数")
    private int rest;
}
