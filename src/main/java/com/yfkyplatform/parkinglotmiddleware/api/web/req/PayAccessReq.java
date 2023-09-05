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
public class PayAccessReq {

    @Schema(name =  "车牌号")
    private String carNo;

    @Schema(name =  "实缴金额,单位：元,若不填则为完整金额")
    private BigDecimal payFee;

    @Schema(name =  "1微信2支付宝")
    private int scanType;

    @Schema(name =  "微信或支付宝openid")
    private String openId;
}
