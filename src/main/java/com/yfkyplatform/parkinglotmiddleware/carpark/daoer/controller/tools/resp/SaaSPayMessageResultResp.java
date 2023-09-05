package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * SaaS 支付信息
 *
 * @author Suhuyuan
 */
@Data
public class SaaSPayMessageResultResp {

    @Schema(title =  "车牌号码")
    private Integer tenantId;

    @Schema(title =  "支付宝配置ID")
    private Long aliThirdId;

    @Schema(title =  "微信配置ID")
    private Long wxThirdId;
}
