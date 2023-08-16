package com.yfkyplatform.parkinglotmiddleware.domain.service.context;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用信息
 *
 * @author Suhuyuan
 */
@Data
public class PayMessage {

    /**
     * 该车计费时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime outTime;

    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime inTime;

    /**
     * 应缴金额,单位分
     */
    private BigDecimal totalFee;
    /**
     * 实缴金额,单位分
     */
    private BigDecimal payFee;
    /**
     * 优惠金额,单位分
     */
    private BigDecimal discountFee;

    private String inId;
}
