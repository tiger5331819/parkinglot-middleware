package com.yfkyplatform.parkinglotmiddleware.api.web.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 车辆支付
 *
 * @author Suhuyuan
 */
@Data
public class PayAccessReq {

    @ApiModelProperty(value = "车牌号")
    private String carNo;

    @ApiModelProperty(value = "实缴金额,单位：元,若不填则为完整金额")
    private BigDecimal payFee;
}