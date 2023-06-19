package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 临时车缴费金额结果
 *
 * @author Suhuyuan
 */
@Data
public class CarFeeResult {
    /**
     * 车牌号码
     */
    private String carNo;

    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inTime;

    /**
     * 计费时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime chargeTime;

    /**
     * 停车时长  单位:分
     */
    private int chargeDuration;

    /**
     * 超时时间  单位:分
     */
    private int overTime;

    /**
     * 需实金额,单位元
     */
    private BigDecimal payCharge;

    /**
     * 营收金额,单位元
     */
    private BigDecimal amount;

    /**
     * 优惠金额,单位元
     */
    private BigDecimal discountAmount;

}
