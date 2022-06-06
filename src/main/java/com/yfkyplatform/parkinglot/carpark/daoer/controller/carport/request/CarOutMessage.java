package com.yfkyplatform.parkinglot.carpark.daoer.controller.carport.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 车辆入场记录通知
 *
 * @author Suhuyuan
 */
@Data
public class CarOutMessage {
    @ApiModelProperty(value = "出场记录ID",required = true)
    private String objectId;
    @ApiModelProperty(value = "车场ID",required = true)
    private String parkingNo;
    @ApiModelProperty(value = "车牌",required = true)
    private String carNo;
    @ApiModelProperty(value = "卡类型",required = true)
    private int cardTypeId;
    @ApiModelProperty(value = "出场时间",required = true)
    private String outTime;
    @ApiModelProperty(value = "出场图片地址",required = true)
    private String outPic;
}
