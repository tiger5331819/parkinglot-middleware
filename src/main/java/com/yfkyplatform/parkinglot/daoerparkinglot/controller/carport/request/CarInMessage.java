package com.yfkyplatform.parkinglot.daoerparkinglot.controller.carport.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 车辆入场记录通知
 *
 * @author Suhuyuan
 */
@Data
public class CarInMessage {
    @ApiModelProperty(value = "入场记录ID",required = true)
    private String objectId;
    @ApiModelProperty(value = "车场ID",required = true)
    private String parkingNo;
    @ApiModelProperty(value = "车牌",required = true)
    private String carNo;
    @ApiModelProperty(value = "卡类型",required = true)
    private int cardTypeId;
    @ApiModelProperty(value = "入场时间",required = true)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private String inTime;
    @ApiModelProperty(value = "入场图片地址",required = true)
    private String inPic;
}
