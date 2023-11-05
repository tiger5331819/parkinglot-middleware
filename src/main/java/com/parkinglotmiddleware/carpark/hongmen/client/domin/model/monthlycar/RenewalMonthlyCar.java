package com.parkinglotmiddleware.carpark.hongmen.client.domin.model.monthlycar;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 会员车辆续费
 *
 * @author Suhuyuan
 */
@Data
public class RenewalMonthlyCar {

    /**
     * 停车场编号
     */
    private String parkingId;
    /**
     * 车牌号码
     */
    private String carPlateNo;
    /**
     * 订单号
     */
    private String parkingOrder;
    /**
     * 支付时间 yyyyMMddHHmmss
     */
    private String paidTime;

    /**
     * 周期
     * <p>
     * 0:天,1:周，2:月,3:年
     */
    private Integer unit;

    /**
     * 数量
     * <p>
     * 如 unit=2,period=3 则表示充值 3 个月
     */
    private Integer period;

    /**
     * 支付金额  单位：分
     */
    private BigDecimal money;

    /**
     * 支付类型
     * <p>
     * 0:现金,1:微信,2:支付宝,3:其它支付平台，4:停车券
     */
    private String payType;

    /**
     * 支付类型 如：“微信”
     */
    private String payOriginDesc;
}
