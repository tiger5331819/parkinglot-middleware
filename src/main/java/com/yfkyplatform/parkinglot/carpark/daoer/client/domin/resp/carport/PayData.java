package com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.carport;

import lombok.Data;


/**
 * 支付信息
 *
 * @author Suhuyuan
 */
@Data
public class PayData {
    /**
     * 实收金额
     */
    private Double payCharge;
    /**
     * 支付方式 0 现金 1微信 2支付宝
     */
    private int payType;
    /**
     * 优惠金额
     */
    private String disAmount;
    /**
     * 订单编号
     */
    private String payOrderNum;
    /**
     * 余额
     */
    private Double balanceMoney;
    /**
     * 应收金额
     */
    private Double accountCharge;
}
