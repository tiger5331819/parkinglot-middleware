package com.parkinglotmiddleware.carpark.hongmen.client.domin.model.coupon;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 折扣卷消费指定车牌
 *
 * @author Suhuyuan
 */
@Data
public class CouponBindCar {

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 车牌号
     */
    private String carPlateNo;

    /**
     * 停车场 ID
     */
    private String parkingId;

    /**
     * 停车流水号
     * <p>
     * 通过查询得到
     */
    private String parkingSerial;
    /**
     * 折扣模版编码
     */
    private Integer proj_code;
    /**
     * 折扣名称
     */
    private String proj_name;
    /**
     * 折扣方式：
     * 0 按折扣金额，
     * 1 按折扣时长，
     * 2 按折扣比例，
     */
    private Integer discount_method;

    /**
     * 按折扣时长  单位分
     */
    private Integer discount_time;

    /**
     * 按折扣金额 单位元
     */
    private BigDecimal discount_money;

    /**
     * 按折扣比例
     * <p>
     * 0-10
     */
    private Integer discount_ratio;
}
