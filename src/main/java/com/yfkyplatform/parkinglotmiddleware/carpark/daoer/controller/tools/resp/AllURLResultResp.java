package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool.BlankCarURL;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool.CarOutPayURL;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 全部车场各类地址生成结果
 *
 * @author Suhuyuan
 */
@Data
public class AllURLResultResp {

    @Schema(name =  "停车场ID")
    private String parkingLotId;

    @Schema(name =  "停车场名称")
    private String parkingLotName;

    @Schema(name =  "健康检查")
    private Boolean healthCheck;

    @Schema(name =  "停车场第三方ID")
    private String parkingLotThirdCode;

    @Schema(name =  "停车场第三方ID")
    private String parkingLotThirdAppName;

    @Schema(name =  "入场通知")
    private String carInUrl;

    @Schema(name =  "批量入场通知")
    private String carInListUrl;

    @Schema(name =  "出场通知")
    private String carOutUrl;

    @Schema(name =  "批量出场通知")
    private String carOutListUrl;

    @Schema(name =  "无牌车入场通知")
    private List<BlankCarURL> blankCarURLList;

    @Schema(name =  "通道出场缴费信息")
    private List<CarOutPayURL> carOutPayURLList;
}
