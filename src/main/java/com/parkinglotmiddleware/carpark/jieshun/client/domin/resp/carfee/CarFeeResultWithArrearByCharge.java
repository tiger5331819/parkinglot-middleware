package com.parkinglotmiddleware.carpark.jieshun.client.domin.resp.carfee;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 临时车缴费金额当前订单结果
 *
 * @author Suhuyuan
 */
@Data
public class CarFeeResultWithArrearByCharge {
    /**
     * 车牌号码
     */
    private String carNo;

    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inTime;

    /**
     * 出场时间 （计费时间） yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime outTime;

    /**
     * 超时时间  单位:分
     */
    private int overTime;

    /**
     * 实收金额,单位元
     */
    private BigDecimal payCharge;

    /**
     * 应收金额,单位元
     */
    private BigDecimal amount;

    /**
     * 优惠金额,单位元
     */
    private BigDecimal discountAmount;

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
