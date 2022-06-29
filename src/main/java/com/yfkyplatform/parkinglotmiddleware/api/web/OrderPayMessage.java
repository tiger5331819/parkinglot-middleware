package com.yfkyplatform.parkinglotmiddleware.api.web;

import com.yfkyframework.common.core.domain.BaseRpcReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 车辆订单缴费信息
 *
 * @author Suhuyuan
 */
@Data
public class OrderPayMessage extends BaseRpcReq {
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    String payTime;
    /**
     * 支付模式类型
     */
    @ApiModelProperty(value = "支付模式类型")
    int paymentType;
    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型")
    int payType;
    /**
     * 支付流水号
     */
    @ApiModelProperty(value = "支付流水号")
    String paymentTransactionId;
    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    Integer payFee;
}
