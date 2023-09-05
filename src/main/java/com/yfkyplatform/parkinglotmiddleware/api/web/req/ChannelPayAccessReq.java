package com.yfkyplatform.parkinglotmiddleware.api.web.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 车辆支付
 *
 * @author Suhuyuan
 */
@Data
public class ChannelPayAccessReq {

    @Schema(title =  "通道Id")
    private String channelId;

    @Schema(title =  "实缴金额,单位：元,若不填则为完整金额")
    private BigDecimal payFee;
}
