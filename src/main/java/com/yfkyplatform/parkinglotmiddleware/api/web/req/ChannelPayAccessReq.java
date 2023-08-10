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
public class ChannelPayAccessReq {

    @ApiModelProperty(value = "通道Id")
    private String channelId;

    @ApiModelProperty(value = "实缴金额,单位：元,若不填则为完整金额")
    private BigDecimal payFee;
}
