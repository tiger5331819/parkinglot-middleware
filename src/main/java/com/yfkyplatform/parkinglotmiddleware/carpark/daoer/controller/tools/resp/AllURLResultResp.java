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

    @Schema(title =  "停车场ID")
    private String parkingLotId;

    @Schema(title =  "停车场名称")
    private String parkingLotName;

    @Schema(title =  "健康检查")
    private Boolean healthCheck;

    @Schema(title =  "停车场第三方ID")
    private String parkingLotThirdCode;

    @Schema(title =  "停车场第三方ID")
    private String parkingLotThirdAppName;

    @Schema(title =  "入场通知")
    private String carInUrl;

    @Schema(title =  "批量入场通知")
    private String carInListUrl;

    @Schema(title =  "出场通知")
    private String carOutUrl;

    @Schema(title =  "批量出场通知")
    private String carOutListUrl;

    @Schema(title =  "无牌车入场通知")
    private List<BlankCarURL> blankCarURLList;

    @Schema(title =  "通道出场缴费信息")
    private List<CarOutPayURL> carOutPayURLList;
}
