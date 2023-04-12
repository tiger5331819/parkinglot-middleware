package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool.BlankCarURL;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool.CarOutPayURL;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 全部车场各类地址生成结果
 *
 * @author Suhuyuan
 */
@Data
public class AllURLResultResp {

    @ApiModelProperty(value = "停车场ID")
    private String parkingLotId;

    @ApiModelProperty(value = "停车场名称")
    private String parkingLotName;

    @ApiModelProperty(value = "停车场第三方ID")
    private String parkingLotThirdCode;

    @ApiModelProperty(value = "无牌车入场通知")
    private List<BlankCarURL> blankCarURLList;

    @ApiModelProperty(value = "通道出场缴费信息")
    private List<CarOutPayURL> carOutPayURLList;
}
