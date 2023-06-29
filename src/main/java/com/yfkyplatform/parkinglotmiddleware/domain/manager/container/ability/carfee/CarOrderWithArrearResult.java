package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee;

import lombok.Data;

/**
 * 临时车缴费订单结果（欠费）
 *
 * @author Suhuyuan
 */
@Data
public class CarOrderWithArrearResult extends CarOrderResult {

    /**
     * 超时时间  单位:分
     */
    private int overTime;

    /**
     * 0 出口缴费 1 定点缴费 2 超时缴费
     */
    private Integer paymentType;

    /**
     * 欠费车场编号，欠费支付需传
     */
    private String parkingNo;

    /**
     * 入场记录唯一标识
     */
    private String inId;
}
