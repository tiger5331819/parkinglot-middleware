package com.yfkyplatform.parkinglotmiddleware.api.web.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 车辆订单缴费信息
 *
 * @author Suhuyuan
 */
@Data
public class OrderPayMessageWithArrear extends OrderPayMessage {

    /**
     * 入场记录ID
     */
    @ApiModelProperty(value = "支付类型")
    private String inId;
}
