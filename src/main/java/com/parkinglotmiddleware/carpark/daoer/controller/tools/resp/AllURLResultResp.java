package com.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool.BlankCarURL;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool.CarOutPayURL;
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

    @Schema(description =  "停车场ID")
    private String parkingLotId;

    @Schema(description =  "停车场名称")
    private String parkingLotName;

    @Schema(description =  "健康检查")
    private Boolean healthCheck;

    @Schema(description =  "停车场第三方ID")
    private String parkingLotThirdCode;

    @Schema(description =  "停车场第三方ID")
    private String parkingLotThirdAppName;

    @Schema(description =  "入场通知")
    private String carInUrl;

    @Schema(description = "联动催缴URL")
    private String dueUrl;

    @Schema(description =  "批量入场通知")
    private String carInListUrl;

    @Schema(description =  "出场通知")
    private String carOutUrl;

    @Schema(description =  "批量出场通知")
    private String carOutListUrl;

    @Schema(description =  "无牌车入场通知")
    private List<BlankCarURL> blankCarURLList;

    @Schema(description =  "通道出场缴费信息")
    private List<CarOutPayURL> carOutPayURLList;
}
