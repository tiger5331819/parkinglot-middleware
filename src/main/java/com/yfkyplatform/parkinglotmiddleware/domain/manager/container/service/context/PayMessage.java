package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 费用信息
 *
 * @author Suhuyuan
 */
@Data
public class PayMessage {

    /**
     * 该车订单创建时间（计费时间） yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime createTime;

    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime inTime;

    /**
     * 超时时间  单位:分
     */
    private int overTime;

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

    /**
     * 入场记录
     */
    private String inId;

    /**
     * 停车时长  单位:分
     */
    public int serviceTime() {
        Duration duration = null;
        if (ObjectUtil.isNull(inTime) || ObjectUtil.isNull(createTime)) {
            duration = Duration.ZERO;
        } else {
            duration = Duration.between(inTime, createTime);

        }
        return new Long(duration.toMinutes()).intValue();
    }
}
