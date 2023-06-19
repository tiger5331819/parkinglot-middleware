package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 临时车缴费订单结果
 *
 * @author Suhuyuan
 */
@Data
public class CarOrderResult {
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    /**
     * 订单创建时间（计费时间） yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 停车时长  单位:分
     */
    private int serviceTime;
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
}
