package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SaaS 支付信息
 *
 * @author Suhuyuan
 */
@Data
public class SaaSPayMessageResultResp {

    @ApiModelProperty(value = "车牌号码")
    private Integer tenantId;

    @ApiModelProperty(value = "支付宝配置ID")
    private Long aliThirdId;

    @ApiModelProperty(value = "微信配置ID")
    private Long wxThirdId;
}
