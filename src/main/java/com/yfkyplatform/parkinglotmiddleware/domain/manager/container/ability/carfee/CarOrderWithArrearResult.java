package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 临时车缴费订单结果（欠费）
 *
 * @author Suhuyuan
 */
@Data
public class CarOrderWithArrearResult {

    /**
     * 车牌号码
     */
    private String carNo;

    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 订单创建时间（计费时间） yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 停车时长  单位:分
     */
    private int serviceTime;

    /**
     * 超时时间  单位:分
     */
    private int overTime;

    /**
     * 应缴金额,单位元
     */
    private BigDecimal totalFee;

    /**
     * 实缴金额,单位元
     */
    private BigDecimal payFee;

    /**
     * 优惠金额,单位元
     */
    private BigDecimal discountFee;

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

    /**
     * 欠费订单
     */
    private List<CarOrderWithArrearResult> arrearList;
}
